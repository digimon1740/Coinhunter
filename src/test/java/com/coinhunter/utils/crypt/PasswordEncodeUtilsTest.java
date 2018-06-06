package com.coinhunter.utils.crypt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PasswordEncodeUtilsTest {

	@Test
	public void encodePasswordTest() {
		String source = "tkdgns8722";
		String encoded = PasswordEncodeUtils.encodePassword(source);
		log.info("encoded : {}", encoded);
	}
}
