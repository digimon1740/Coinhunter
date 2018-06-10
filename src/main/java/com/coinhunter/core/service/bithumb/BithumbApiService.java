package com.coinhunter.core.service.bithumb;

import com.coinhunter.client.bithumb.BithumbApiClient;
import com.coinhunter.core.domain.bithumb.BithumbTicker;
import com.coinhunter.core.domain.user.ApiKey;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.api.ApiKeyService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import com.coinhunter.core.service.user.UserService;
import com.coinhunter.utils.bithumb.BithumbClientUtils;
import com.coinhunter.utils.http.HttpClient;
import com.coinhunter.utils.jackson.JacksonJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class BithumbApiService {

	@Value("${bithumb.base.url}")
	private String baseUrl; // Base URL

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
		BithumbApiClient api = new BithumbApiClient(baseUrl, apiKey.getAccessKey(), apiKey.getSecretKey());
		String result = null;
		try {
			result = api.callApi(url, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	private String fetchResultfFromPublicApi(String url, Map<String, String> map) {
		HttpClient client = new HttpClient();
		String params = BithumbClientUtils.mapToQueryString(map);
		return client.get(url, params);
	}

	public BithumbTicker getTickerByCryptoCurrency(CryptoCurrency cryptoCurrency) {
		String apiUrl = baseUrl + tickerUrl + cryptoCurrency.name();
		try {
			String result = fetchResultfFromPublicApi(apiUrl, null);
			return jacksonJsonUtils.readValue(result, BithumbTicker.class);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return new BithumbTicker("5900", Collections.emptyMap());
	}
}
