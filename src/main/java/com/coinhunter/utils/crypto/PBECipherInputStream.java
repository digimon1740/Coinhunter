package com.coinhunter.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class PBECipherInputStream extends java.io.FilterInputStream {

	private CipherInputStream cis;

	public PBECipherInputStream(InputStream in, char[] password) throws IOException, GeneralSecurityException {
		super(in);

		Key key = readKey(in, password);
		AlgorithmParameterSpec params = readIv(in);

		Cipher cipher = Cipher.getInstance(PBECipherUtils.TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, key, params);

		if (!(in instanceof BufferedInputStream)) {
			in = new BufferedInputStream(in);
		}
		cis = new CipherInputStream(in, cipher);
	}

	private Key readKey(InputStream in, char[] password) throws IOException, GeneralSecurityException {
		byte[] salt = new byte[PBECipherUtils.SALT_LEN];
		in.read(salt);

		byte[] encryptedKeyBytes = new byte[PBECipherUtils.ENCRYPTED_KEY_LEN];
		in.read(encryptedKeyBytes, 0, encryptedKeyBytes.length);

		PBECipherUtils pbeCipher = new PBECipherUtils(PBECipherUtils.PBE_TRANSFORMATION);
		pbeCipher.init(Cipher.DECRYPT_MODE, password, salt, PBECipherUtils.ITERATION_COUNT);

		byte[] decryptedKeyBytes = pbeCipher.doFinal(encryptedKeyBytes);

		SecretKeySpec key = new SecretKeySpec(decryptedKeyBytes, PBECipherUtils.ALGORITHM);
		return key;
	}

	private IvParameterSpec readIv(InputStream in) throws IOException {
		byte[] iv = new byte[PBECipherUtils.IV_LEN];
		in.read(iv, 0, iv.length);
		return new IvParameterSpec(iv);
	}

	public int read() throws IOException {
		return cis.read();
	}

	public int read(byte b[]) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte b[], int off, int len) throws IOException {
		return cis.read(b, off, len);
	}

	public long skip(long n) throws IOException {
		return cis.skip(n);
	}

	public int available() throws IOException {
		return cis.available();
	}

	public void close() throws IOException {
		cis.close();
	}

	public synchronized void mark(int readlimit) {
		cis.mark(readlimit);
	}

	public synchronized void reset() throws IOException {
		cis.reset();
	}

	public boolean markSupported() {
		return cis.markSupported();
	}

}
