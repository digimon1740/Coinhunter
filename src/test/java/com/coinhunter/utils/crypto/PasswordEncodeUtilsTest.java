package com.coinhunter.utils.crypto;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PasswordEncodeUtilsTest {

	@Test
	public void encodePasswordTest() {
		String source = "tkdgns8722";
//		$2a$10$/IalnT7jZwj5SSU0t/aUA.RBOiMiDFM9WWbieJYpQ6drlwsZT0YUy
// $2a$10$7j83gYgFN0GvRqFFXcGzh.yrFOfuMO1QrU1IDN6WiyWWBkY4K3cDq
		String encoded = PasswordEncodeUtils.encodePassword(source);
		log.info("encoded : {}", encoded);
	}
}
