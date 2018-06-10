package com.coinhunter.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HttpClientTest {

	private HttpClient httpClient = new HttpClient();

	@Test
	public void getTest() {
		long stratTime = System.currentTimeMillis();
		String result = httpClient.get("https://api.bithumb.com/public/ticker/EOS");
		log.info("result : {}", result);
		long endTime = System.currentTimeMillis() - stratTime;
		log.info("endTime : {}", endTime);
	}
}
