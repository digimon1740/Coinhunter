package com.coinhunter.utils.crypto;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class PBECipherUtilsTests {

	private static final String key = "abcd";

	@Test
	public void encryptTextTest() {
		String encodedText = PBECipherUtils.encryptText(key, "1234");
		Assert.assertNotNull(encodedText);
		log.info("encodedText : {}", encodedText);
	}

	@Test
	public void decryptTextTest() {
		String cipherText = "42mzK4EYetA6jKGIyXqeAIZw3M8U1v/7IzOtpXvNVQOKC1TkIiOfvzfL6IRhQ9MyvemDKtDNN4SZqBlkpfoxzQ==";
		String decodedText = PBECipherUtils.decryptText(key, cipherText);
		Assert.assertNotNull(decodedText);
		Assert.assertEquals(decodedText, "1234");
		log.info("decodedText : {}", decodedText);
	}
}