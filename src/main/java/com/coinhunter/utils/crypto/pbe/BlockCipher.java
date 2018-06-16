package com.coinhunter.utils.crypto.pbe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.KeySpec;

/**
 * <a href=
 * "http://download.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher"
 * >Java &trade; Cryptography Architecture(JCA) Reference Guide</a>
 * 
 * @author SangHoon Lee
 */
public class BlockCipher extends GeneralCipher {

	/**
	 * 
	 * @param transformation
	 *            is of the form: "algorithm/mode/padding" or "algorithm" <br/>
	 *            For example, the following are valid transformations:
	 *            "DES/ECB/PKCS5Padding" or "DES"
	 * @throws GeneralSecurityException
	 */
	public BlockCipher(String transformation) throws GeneralSecurityException {
		super(transformation);
	}

	public Key generateKey(String transformation, byte[] keyBytes) throws GeneralSecurityException {
		String algorithm = transformation;
		if (transformation.indexOf('/') > -1) {
			algorithm = algorithm.substring(0, algorithm.indexOf('/'));
		}
		// java.security.spec.KeySpec
		if ("DES".equalsIgnoreCase(algorithm)) {
			byte[] key = new byte[DESKeySpec.DES_KEY_LEN];
			System.arraycopy(keyBytes, 0, key, 0, key.length > keyBytes.length ? keyBytes.length : key.length);
			KeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else if ("DESede".equalsIgnoreCase(algorithm) || "TripleDES".equalsIgnoreCase(algorithm)) {
			byte[] key = new byte[DESedeKeySpec.DES_EDE_KEY_LEN];
			System.arraycopy(keyBytes, 0, key, 0, key.length > keyBytes.length ? keyBytes.length : key.length);
			KeySpec keySpec = new DESedeKeySpec(key);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else { // AES
			int keyLength = Cipher.getMaxAllowedKeyLength(transformation);
			byte[] key = new byte[keyLength];
			System.arraycopy(keyBytes, 0, key, 0, key.length > keyBytes.length ? keyBytes.length : key.length);
			SecretKey secretKey = new SecretKeySpec(key, algorithm);
			return secretKey;
		}
	}

	/**
	 * Initializes this cipher with a key.
	 * 
	 * @param opmode
	 *            the operation mode of this cipher (this is one of the
	 *            following: ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE or
	 *            UNWRAP_MODE)
	 * @param keyBytes
	 *            the key
	 * @throws GeneralSecurityException
	 * @see Cipher#init(int, Key)
	 */
	public void init(int opmode, byte[] keyBytes) throws GeneralSecurityException {
		Key key = generateKey(cipher.getAlgorithm(), keyBytes);
		init(opmode, key);
	}

	public String encryptText(String plainText) throws IOException, GeneralSecurityException {
		byte[] b = plainText.getBytes("UTF-8");
		b = doFinal(b);
		String encryptedText = encodeBASE64(b);
		return encryptedText;
	}

	public String decryptText(String encryptedText) throws IOException, GeneralSecurityException {
		byte[] b = decodeBASE64(encryptedText);
		b = doFinal(b);
		String decryptedText = new String(b, "UTF-8");
		return decryptedText;
	}
}
