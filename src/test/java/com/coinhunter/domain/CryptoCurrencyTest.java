package com.coinhunter.domain;

import com.coinhunter.domain.value.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CryptoCurrencyTest {

	@Test
	public void getAllCurrenciesTest() {
		List<CryptoCurrency> allCurrencies = CryptoCurrency.getAllCurrencies();
		Assert.assertNotNull(allCurrencies);
		allCurrencies.stream().forEach(currency -> log.info("coin : {}", currency.name()));
	}
}
