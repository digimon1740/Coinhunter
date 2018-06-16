package com.coinhunter.core.service.bithumb;


import com.coinhunter.core.domain.bithumb.chart.BithumbChart;
import com.coinhunter.core.domain.bithumb.myassets.BithumbBalance;
import com.coinhunter.core.domain.bithumb.myassets.BithumbMyAssets;
import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
		Assert.assertNotNull(bithumbChart);
		Assert.assertTrue(bithumbChart.isSuccess());
		log.info("bithumbChart : {}", bithumbChart);
	}

	@Test
	public void getBalanceByUserIdTest() {
		BithumbBalance bithumbBalance = bithumbApiService.getBalanceByUserId(1, CryptoCurrency.EOS);
		Assert.assertNotNull(bithumbBalance);
		log.info("bithumbBalance : {}", bithumbBalance);
	}

	@Test
	public void getMyAssetsByUserIdTest() {
		BithumbMyAssets bithumbMyAssets = bithumbApiService.getMyAssetsByUserId(1);
		Assert.assertNotNull(bithumbMyAssets);
		Assert.assertTrue(bithumbMyAssets.isSuccess());
		log.info("bithumbMyAssets : {}", bithumbMyAssets);
	}


}