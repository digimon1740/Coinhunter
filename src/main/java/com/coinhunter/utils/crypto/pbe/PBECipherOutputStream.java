package com.coinhunter.utils.crypto.pbe;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class PBECipherOutputStream extends FilterOutputStream {

	private CipherOutputStream cos;

	public PBECipherOutputStream(OutputStream out, char[] password) throws IOException, GeneralSecurityException {
		super(out);

		Key key = writeKey(out, password);
		AlgorithmParameterSpec params = writeIv(out);

		Cipher cipher = Cipher.getInstance(PBECipherUtils.TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, key, params);

		if (!(out instanceof BufferedOutputStream)) {
			out = new BufferedOutputStream(out);
		}
		cos = new CipherOutputStream(out, cipher);
	}

	private Key writeKey(OutputStream out, char[] password) throws GeneralSecurityException, IOException {
		byte[] salt = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		out.write(salt, 0, salt.length);

		PBECipherUtils pbeCipher = new PBECipherUtils(PBECipherUtils.PBE_TRANSFORMATION);
		pbeCipher.init(Cipher.ENCRYPT_MODE, password, salt, PBECipherUtils.ITERATION_COUNT);

		Key key = pbeCipher.generateKey(PBECipherUtils.ALGORITHM);
		byte[] encodedKeyBytes = key.getEncoded();
		byte[] encryptedKeyBytes = pbeCipher.doFinal(encodedKeyBytes);

		out.write(encryptedKeyBytes, 0, encryptedKeyBytes.length);

		return key;
	}

	private IvParameterSpec writeIv(OutputStream out) throws IOException {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[PBECipherUtils.IV_LEN];
		random.nextBytes(iv);

		out.write(iv, 0, iv.length);

		return new IvParameterSpec(iv);
	}

	public void write(int b) throws IOException {
		cos.write(b);
	}

	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte b[], int off, int len) throws IOException {
		if ((off | len | (b.length - (len + off)) | (off + len)) < 0)
			throw new IndexOutOfBoundsException();

		for (int i = 0; i < len; i++) {
			write(b[off + i]);
		}
	}

	public void flush() throws IOException {
		cos.flush();
	}

	public void close() throws IOException {
		try {
			flush();
		} catch (IOException ignored) {
		}
		cos.close();
	}

}
