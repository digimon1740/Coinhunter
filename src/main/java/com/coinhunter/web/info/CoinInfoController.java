package com.coinhunter.web.info;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CoinInfoController {

	@Autowired
	public CoinInfoController() {
	}

	@GetMapping("/coins")
	public List<CryptoCurrency> findAllCryptoCurrencies() {
		return CryptoCurrency.getAllCurrencies();
	}
}