package com.coinhunter.service.bithumb;

import com.coinhunter.client.bithumb.BithumbApiClient;
import com.coinhunter.domain.bithumb.BithumbTicker;
import com.coinhunter.domain.user.ApiKey;
import com.coinhunter.domain.value.CryptoCurrency;
import com.coinhunter.service.api.ApiKeyService;
import com.coinhunter.service.user.UserDetailsServiceImpl;
import com.coinhunter.service.user.UserService;
import com.coinhunter.utils.jackson.JacksonJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BithumbApiService {

	@Value("${bithumb.base.url}")
	private String apiUrl; // Base URL

	@Value("${bithumb.ticker.url}")
	private String tickerUrl; // 거래소 마지막 거래 정보

	@Value("${bithumb.market.list.url}")
	private String marketListUrl; // 호가 정보

	@Value("${bithumb.account.url}")
	private String accountUrl; // 계정 정보

	@Value("${bithumb.balance.url}")
	private String balanceUrl; // 사용가능한 잔액조회

	private UserService userService;

	private UserDetailsServiceImpl userDetailsService;

	private ApiKeyService apiKeyService;

	private JacksonJsonUtils jacksonJsonUtils;

	@Autowired
	public BithumbApiService(UserService userService,
	                         UserDetailsServiceImpl userDetailsService,
	                         ApiKeyService apiKeyService,
	                         JacksonJsonUtils jacksonJsonUtils) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.apiKeyService = apiKeyService;
		this.jacksonJsonUtils = jacksonJsonUtils;
	}

	private String fetchResultFromApi(String url, ApiKey apiKey, Map<String, String> params) {
		BithumbApiClient api = new BithumbApiClient(apiUrl, apiKey.getAccessKey(), apiKey.getSecretKey());
		String result = null;
		try {
			result = api.callApi(url, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public BithumbTicker getTickerByCryptoCurrency(long userId, CryptoCurrency cryptoCurrency) {
		Map<String, Object> map = new HashMap<>();
		map.put("currency", cryptoCurrency.name());
		StringSubstitutor sub = new StringSubstitutor(map);
		tickerUrl = tickerUrl.replace("!", "$");
		String resolvedURL = sub.replace(tickerUrl);

		try {
			ApiKey apiKey = apiKeyService.findByUserId(userId);
			String result = fetchResultFromApi(resolvedURL, apiKey, null);
			return jacksonJsonUtils.readValue(result, BithumbTicker.class);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return new BithumbTicker(-1, "5900", Collections.emptyMap());
	}
}
