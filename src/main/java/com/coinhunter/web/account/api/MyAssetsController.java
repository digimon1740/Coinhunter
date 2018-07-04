package com.coinhunter.web.account.api;

import com.coinhunter.core.domain.bithumb.myassets.BithumbBalance;
import com.coinhunter.core.domain.bithumb.myassets.BithumbMyAssets;
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
@RequestMapping("/my-assets")
public class MyAssetsController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	@Autowired
	public MyAssetsController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
	}

	@GetMapping("/bithumb")
	public BithumbMyAssets findMyAssets() {
		return bithumbApiService.getMyAssetsByUserId(userDetailsService.getUserId());
	}

	@GetMapping("/bithumb/{cryptoCurrency}")
	public BithumbBalance findMyAsset(@PathVariable(name = "cryptoCurrency") String cryptoCurrency) {
		return bithumbApiService.getBalanceByUserId(userDetailsService.getUserId(), CryptoCurrency.of(cryptoCurrency));
	}
}