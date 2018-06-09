package com.coinhunter.web.trading.api;

import com.coinhunter.domain.bithumb.BithumbTicker;
import com.coinhunter.domain.value.CryptoCurrency;
import com.coinhunter.service.bithumb.BithumbApiService;
import com.coinhunter.service.user.UserDetailsServiceImpl;
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

	@GetMapping("/ticker/{cryptoCurrency}")
	public BithumbTicker ticker(@PathVariable("cryptoCurrency") String cryptoCurrency) {
		long userId = userDetailsService.getUserId();
		BithumbTicker bithumbTicker = bithumbApiService.getTickerByCryptoCurrency(userId, CryptoCurrency.of(cryptoCurrency));
		// TODO 테스트 후 userId 제거 필요
		bithumbTicker.setUserId(userId);
		return bithumbTicker;
	}
}