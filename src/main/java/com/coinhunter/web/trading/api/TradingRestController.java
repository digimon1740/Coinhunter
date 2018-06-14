package com.coinhunter.web.trading.api;

import com.coinhunter.core.domain.bithumb.BithumbChart;
import com.coinhunter.core.domain.bithumb.BithumbTicker;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public BithumbTicker findTicker(@PathVariable("cryptoCurrency") String cryptoCurrency) {
		return bithumbApiService.getTickerByCryptoCurrency(CryptoCurrency.of(cryptoCurrency));
	}

	@GetMapping("/bithumb/chart")
	public BithumbChart findChart(@RequestParam(name = "cryptoCurrency") String cryptoCurrency,
	                              @RequestParam(name = "period") String period) {
		return bithumbApiService.getChartData(CryptoCurrency.of(cryptoCurrency), period);
	}
}