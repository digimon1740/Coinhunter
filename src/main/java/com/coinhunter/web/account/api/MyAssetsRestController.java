package com.coinhunter.web.account.api;

import com.coinhunter.core.domain.bithumb.chart.BithumbChart;
import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/my-assets")
public class MyAssetsRestController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	@Autowired
	public MyAssetsRestController(
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