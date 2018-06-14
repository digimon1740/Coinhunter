package com.coinhunter.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HttpClientTest {

	private HttpClient httpClient = new HttpClient();

	@Test
	public void getTest() {
		//0 : 1  H:3 L :4 c:2 rate : 5
		long startTime = System.currentTimeMillis();
		//https://www.bithumb.com/resources/chart/XEM_xcoinTrade_10M.json?symbol=XEM&resolution=0.5&from=1527862876&to=1528726936&strTime=1528726876754
		//https://www.bithumb.com/resources/chart/BTC_xcoinTrade_10M.json?symbol=BTC&resolution=0.5&from=1527862257&to=1528726317&strTime=1528726257216
		String result = httpClient.get("https://api.bithumb.com/public/ticker/EOS");
		log.info("result : {}", result);
		long endTime = System.currentTimeMillis() - startTime;
		log.info("endTime : {}", endTime);
	}
}
