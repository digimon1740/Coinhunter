package com.coinhunter.utils.crypto.pbe;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

public abstract class GeneralCipher {

	Cipher cipher;

	public GeneralCipher(String transformation) throws GeneralSecurityException {
		try {
			cipher = Cipher.getInstance(transformation);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (NoSuchPaddingException e) {
			throw e;
		}
	}

	public Cipher getCipher() {
		return cipher;
	}

	public void init(int opmode, Key key) throws GeneralSecurityException {
		try {
			cipher.init(opmode, key);
		} catch (InvalidKeyException e) {
			throw e;
		}
	}

	public void init(int opmode, Key key, AlgorithmParameterSpec params) throws GeneralSecurityException {
		try {
			cipher.init(opmode, key, params);
		} catch (InvalidKeyException e) {
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			throw e;
		}
	}

	/**
	 * Encrypts or decrypts data in a single-part operation, or finishes a
	 * multiple-part operation. The data is encrypted or decrypted, depending on
	 * how this cipher was initialized.
	 * <p>
	 * <p>
	 * Note: if any exception is thrown, this cipher object may need to be reset
	 * before it can be used again.
	 *
	 * @param input the input buffer
	 * @return the new buffer with the result
	 * @see Cipher#doFinal(byte[])
	 */
	public byte[] doFinal(byte[] input) throws GeneralSecurityException {
		try {
			return cipher.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
	}

	/**
	 * Encrypts or decrypts data in a single-part operation, or finishes a
	 * multiple-part operation. The data is encrypted or decrypted, depending on
	 * how this cipher was initialized.
	 *
	 * @param input       the input buffer
	 * @param inputOffset the offset in <code>input</code> where the input starts
	 * @param inputLen    the input length
	 * @return the new buffer with the result
	 * @see Cipher#doFinal(byte[], int, int)
	 */
	public final byte[] doFinal(byte[] input, int inputOffset, int inputLen) throws GeneralSecurityException {
		try {
			return cipher.doFinal(input, inputOffset, inputLen);
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
	}

	public static String encodeBASE64(byte[] bytes) {
		Base64 base64 = new Base64();
		String encoded = new String(base64.encode(bytes));
		return encoded.replaceAll("\\r\\n|\\r|\\n", "");
	}

	public static byte[] decodeBASE64(String base64Text) {
		Base64 base64 = new Base64();
		return base64.decode(base64Text);
	}

}
