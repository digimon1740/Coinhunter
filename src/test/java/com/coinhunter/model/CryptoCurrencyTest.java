package com.coinhunter.model;

import com.coinhunter.model.value.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CryptoCurrencyTest {

	@Test
	public void getAllCurrenciesTest() {
		log.info("all coins : {}", CryptoCurrency.getAllCurrencies());
	}
}
