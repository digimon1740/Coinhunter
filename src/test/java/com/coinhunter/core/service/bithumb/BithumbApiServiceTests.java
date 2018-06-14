package com.coinhunter.core.service.bithumb;


import com.coinhunter.core.domain.bithumb.BithumbChart;
import com.coinhunter.core.domain.bithumb.BithumbTicker;
import com.coinhunter.core.domain.user.ApiKey;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.api.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BithumbApiServiceTests {

	@Autowired
	private BithumbApiService bithumbApiService;

	@Test
	public void getTickerByCryptoCurrencyTest() {
		BithumbTicker bithumbTicker = bithumbApiService.getTickerByCryptoCurrency(CryptoCurrency.EOS);
		Assert.assertNotNull(bithumbTicker);
		log.info("bithumbTicker : {}", bithumbTicker);
	}

	@Test
	public void getChartDataTest() {
		BithumbChart bithumbChart = bithumbApiService.getChartData(CryptoCurrency.EOS, "10M");
		log.info("bithumbChart : {}", bithumbChart);
	}
}