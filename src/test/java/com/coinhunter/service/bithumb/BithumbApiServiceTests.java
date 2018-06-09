package com.coinhunter.service.bithumb;


import com.coinhunter.domain.bithumb.BithumbTicker;
import com.coinhunter.domain.user.ApiKey;
import com.coinhunter.domain.value.CryptoCurrency;
import com.coinhunter.service.api.ApiKeyService;
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
		BithumbTicker bithumbTicker = bithumbApiService.getTickerByCryptoCurrency(1, CryptoCurrency.EOS);
		Assert.assertNotNull(bithumbTicker);
		log.info("bithumbTicker : {}", bithumbTicker);

		Assert.assertTrue(bithumbTicker.connecionSucceded());

		long openingPrice = bithumbTicker.getOpeningPrice();
		log.info("openingPrice : {}", openingPrice);

		long closingPrice = bithumbTicker.getClosingPrice();
		log.info("closingPrice : {}", closingPrice);

		long minPrice = bithumbTicker.getMinPrice();
		log.info("minPrice : {}", minPrice);

		long maxPrice = bithumbTicker.getMaxPrice();
		log.info("maxPrice : {}", maxPrice);

		double averagePrice = bithumbTicker.getAveragePrice();
		log.info("averagePrice : {}", averagePrice);

		double unitsTraded = bithumbTicker.getUnitsTraded();
		log.info("unitsTraded : {}", unitsTraded);

		double volume1Day = bithumbTicker.getVolume1Day();
		log.info("volume1Day : {}", volume1Day);

		double volume7Day = bithumbTicker.getVolume7Day();
		log.info("volume7Day : {}", volume7Day);

		long buyPrice = bithumbTicker.getBuyPrice();
		log.info("buyPrice : {}", buyPrice);

		long sellPrice = bithumbTicker.getSellPrice();
		log.info("sellPrice : {}", sellPrice);

		long fluctate = bithumbTicker.get24HFluctate();
		log.info("fluctate : {}", fluctate);

		double fluctateRate = bithumbTicker.get24HFluctateRate();
		log.info("fluctateRate : {}", fluctateRate);

		LocalDateTime date = bithumbTicker.getDate();
		log.info("date : {}", date);
	}
}