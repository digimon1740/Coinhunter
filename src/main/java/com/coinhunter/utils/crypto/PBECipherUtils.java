package com.coinhunter.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PBECipherUtils extends GeneralCipher {

	static final String PBE_TRANSFORMATION = "PBEWithSHA1AndDESede";

	static final String ALGORITHM = "AES";

	static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

	static final int ITERATION_COUNT = 100;

	static final int SALT_LEN = 8;

	static final int IV_LEN = 16;

	static final int ENCRYPTED_KEY_LEN = 24;

	private SecureRandom random = new SecureRandom();

	public PBECipherUtils(String transformation) throws GeneralSecurityException {
		super(transformation);
	}

	private SecretKey generateSecret(char[] password) throws GeneralSecurityException {
		String algorithm = cipher.getAlgorithm(); // PBEWithSHA1AndDESede
		if (algorithm.indexOf('/') > -1) {
			algorithm = algorithm.substring(0, algorithm.indexOf('/'));
		}
		KeySpec keySpec = new PBEKeySpec(password);
		SecretKeyFactory keyFactory = null;
		try {
			keyFactory = SecretKeyFactory.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		SecretKey key = null;
		try {
			key = keyFactory.generateSecret(keySpec);
		} catch (InvalidKeySpecException e) {
			throw e;
		}
		return key;
	}

	public void init(int opmode, char[] password, byte[] salt, int iterationCount) throws GeneralSecurityException {
		Key key = generateSecret(password);
		AlgorithmParameterSpec params = new PBEParameterSpec(salt, iterationCount);
		init(opmode, key, params);
	}

	public Key generateKey(String algorithm) throws GeneralSecurityException {
		if (algorithm.indexOf('/') > -1) {
			algorithm = algorithm.substring(0, algorithm.indexOf('/'));
		}
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		int keysize = 0;
		if ("DES".equalsIgnoreCase(algorithm)) {
			keysize = DESKeySpec.DES_KEY_LEN * 8;
		} else if ("DESede".equalsIgnoreCase(algorithm) || "TripleDES".equalsIgnoreCase(algorithm)) {
			keysize = DESedeKeySpec.DES_EDE_KEY_LEN * 8;
		} else {
			try {
				keysize = Cipher.getMaxAllowedKeyLength(algorithm);
			} catch (NoSuchAlgorithmException e) {
				throw e;
			}
			if (keysize == Integer.MAX_VALUE) {
				if ("AES".equalsIgnoreCase(algorithm)) {
					keysize = 16 * 8;
				}
			}
		}
		keyGenerator.init(keysize);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	public byte[] getRandomSalt() {
		byte[] salt = new byte[SALT_LEN];
		random.nextBytes(salt);
		return salt;
	}

	public byte[] getRandomIV() {
		byte[] iv = new byte[IV_LEN];
		random.nextBytes(iv);
		return iv;
	}

	public static byte[] encrypt(char[] password, byte[] input) throws GeneralSecurityException {
		PBECipherUtils pbeCipher = new PBECipherUtils(PBECipherUtils.PBE_TRANSFORMATION);

		byte[] salt = pbeCipher.getRandomSalt();

		pbeCipher.init(Cipher.ENCRYPT_MODE, password, salt, PBECipherUtils.ITERATION_COUNT);

		Key key = pbeCipher.generateKey(PBECipherUtils.ALGORITHM);
		byte[] encodedKeyBytes = key.getEncoded();
		byte[] encryptedKeyBytes = pbeCipher.doFinal(encodedKeyBytes);

		byte[] iv = pbeCipher.getRandomIV();

		AlgorithmParameterSpec params = new IvParameterSpec(iv);

		BlockCipher cipher = new BlockCipher(PBECipherUtils.TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, key, params);

		byte[] output = cipher.doFinal(input);

		byte[] result = new byte[salt.length + encryptedKeyBytes.length + iv.length + output.length];

		System.arraycopy(salt, 0, result, 0, salt.length);
		System.arraycopy(encryptedKeyBytes, 0, result, salt.length, encryptedKeyBytes.length);
		System.arraycopy(iv, 0, result, salt.length + encryptedKeyBytes.length, iv.length);
		System.arraycopy(output, 0, result, salt.length + encryptedKeyBytes.length + iv.length, output.length);

		return result;
	}

	public static byte[] decrypt(char[] password, byte[] input) throws GeneralSecurityException {
		byte[] salt = new byte[PBECipherUtils.SALT_LEN];
		System.arraycopy(input, 0, salt, 0, salt.length);

		PBECipherUtils pbeCipher = new PBECipherUtils(PBECipherUtils.PBE_TRANSFORMATION);
		pbeCipher.init(Cipher.DECRYPT_MODE, password, salt, PBECipherUtils.ITERATION_COUNT);

		byte[] encryptedKeyBytes = new byte[PBECipherUtils.ENCRYPTED_KEY_LEN];
		System.arraycopy(input, salt.length, encryptedKeyBytes, 0, encryptedKeyBytes.length);
		byte[] decryptedKeyBytes = pbeCipher.doFinal(encryptedKeyBytes);

		Key key = new SecretKeySpec(decryptedKeyBytes, PBECipherUtils.ALGORITHM);

		byte[] iv = new byte[PBECipherUtils.IV_LEN];
		System.arraycopy(input, salt.length + encryptedKeyBytes.length, iv, 0, iv.length);

		AlgorithmParameterSpec params = new IvParameterSpec(iv);

		BlockCipher cipher = new BlockCipher(PBECipherUtils.TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, key, params);

		int inputOffset = salt.length + encryptedKeyBytes.length + iv.length;
		int inputLen = input.length - inputOffset;

		return cipher.doFinal(input, inputOffset, inputLen);
	}

	public static String encryptText(String password, String cleartext) {
		byte[] b = null;
		try {
			b = cleartext.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return cleartext;
		}
		try {
			b = encrypt(password.toCharArray(), b);
			return encodeBASE64(b);
		} catch (GeneralSecurityException e) {
			return cleartext;
		}
	}

	public static String decryptText(String password, String ciphertext) {
		byte[] b = null;
		b = decodeBASE64(ciphertext);
		try {
			b = decrypt(password.toCharArray(), b);
			return new String(b, "UTF-8");
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			return ciphertext;
		}
	}
}
