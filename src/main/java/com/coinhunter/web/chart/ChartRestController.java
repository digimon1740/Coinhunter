package com.coinhunter.web.chart;

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
@RequestMapping("/chart")
public class ChartRestController {

	private BithumbApiService bithumbApiService;

	@Autowired
	public ChartRestController(BithumbApiService bithumbApiService) {
		this.bithumbApiService = bithumbApiService;
	}

	@GetMapping("/bithumb")
	public BithumbChart findChart(@RequestParam(name = "cryptoCurrency") String cryptoCurrency,
	                              @RequestParam(name = "period") String period) {
		return bithumbApiService.getChartData(CryptoCurrency.of(cryptoCurrency), period);
	}
}