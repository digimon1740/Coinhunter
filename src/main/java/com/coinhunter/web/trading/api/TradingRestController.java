package com.coinhunter.web.trading.api;

import com.coinhunter.core.domain.bithumb.BithumbTicker;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/trading")
public class TradingRestController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	@Autowired
	public TradingRestController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
	}

	@GetMapping("/bithumb/ticker/{cryptoCurrency}")
	public BithumbTicker ticker(@PathVariable("cryptoCurrency") String cryptoCurrency) {
		return bithumbApiService.getTickerByCryptoCurrency(CryptoCurrency.of(cryptoCurrency));
	}
}