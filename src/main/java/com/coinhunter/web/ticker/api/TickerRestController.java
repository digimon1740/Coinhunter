package com.coinhunter.web.ticker.api;

import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ticker")
public class TickerRestController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	@Autowired
	public TickerRestController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
	}

	@GetMapping("/bithumb/{cryptoCurrency}")
	public BithumbTicker findTicker(@PathVariable("cryptoCurrency") String cryptoCurrency) {
		return bithumbApiService.getTickerByCryptoCurrency(CryptoCurrency.of(cryptoCurrency));
	}
}