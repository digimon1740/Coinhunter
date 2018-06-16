package com.coinhunter.core.service.bithumb;

import com.coinhunter.client.bithumb.BithumbApiClient;
import com.coinhunter.core.domain.bithumb.BithumbApiDetails;
import com.coinhunter.core.domain.bithumb.chart.BithumbChart;
import com.coinhunter.core.domain.bithumb.myassets.BithumbBalance;
import com.coinhunter.core.domain.bithumb.myassets.BithumbMyAssets;
import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.domain.user.ApiKey;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.api.ApiKeyService;
import com.coinhunter.core.service.message.MessageSourceService;
import com.coinhunter.utils.bithumb.BithumbClientUtils;
import com.coinhunter.utils.config.JsonConfig;
import com.coinhunter.utils.http.HttpClient;
import com.coinhunter.utils.jackson.JacksonJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BithumbApiService {

	private ApiKeyService apiKeyService;

	private MessageSourceService messageSourceService;

	private JsonConfig jsonConfig;

	private JacksonJsonUtils jacksonJsonUtils;

	private BithumbApiDetails bithumbApiDetails;

	@Autowired
	public BithumbApiService(ApiKeyService apiKeyService,
	                         MessageSourceService messageSourceService,
	                         JsonConfig jsonConfig,
	                         JacksonJsonUtils jacksonJsonUtils) {
		this.apiKeyService = apiKeyService;
		this.messageSourceService = messageSourceService;
		this.jsonConfig = jsonConfig;
		this.jacksonJsonUtils = jacksonJsonUtils;
		this.bithumbApiDetails = jsonConfig.getBithumbApiDetails();
	}

	private String fetchResultFromPrivateApi(String requestUrl, ApiKey apiKey, Map<String, String> params) {
		BithumbApiClient api = new BithumbApiClient(bithumbApiDetails.getBaseUrl(), apiKey.getAccessKey(), apiKey.getSecretKey());
		String result = null;
		try {
			result = api.callApi(requestUrl, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	private String fetchResultFromPublicApi(String url, Map<String, String> map) {
		HttpClient client = new HttpClient();
		String params = BithumbClientUtils.mapToQueryString(map);
		return client.get(url, params);
	}

	public BithumbTicker getTickerByCryptoCurrency(CryptoCurrency cryptoCurrency) {
		String apiUrl = bithumbApiDetails.getBaseUrl() + bithumbApiDetails.getTicker();
		apiUrl = apiUrl.replace("{cryptoCurrency}", cryptoCurrency.name());
		try {
			String result = fetchResultFromPublicApi(apiUrl, null);
			BithumbTicker bithumbTicker = jacksonJsonUtils.readValue(result, BithumbTicker.class);
			bithumbTicker.setCryptoCurrency(cryptoCurrency);
			return bithumbTicker;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return new BithumbTicker("5900", cryptoCurrency, Collections.emptyMap());
	}

	public BithumbChart getChartData(CryptoCurrency cryptoCurrency, String period) {
		String apiUrl = bithumbApiDetails.getChart();
		apiUrl = apiUrl.replace("{cryptoCurrency}", cryptoCurrency.name());
		apiUrl = apiUrl.replace("{period}", period);
		apiUrl = apiUrl.replace("{timeStamp}", String.valueOf(System.currentTimeMillis()));

		try {
			String result = fetchResultFromPublicApi(apiUrl, null);
			return new BithumbChart(true, cryptoCurrency, result);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return new BithumbChart(false, cryptoCurrency, null);
	}

	public BithumbBalance getBalanceByUserId(long userId, CryptoCurrency cryptoCurrency) {
		ApiKey apiKey = apiKeyService.findByUserId(userId);
		Assert.notNull(apiKey, messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getAccessKey(), messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getSecretKey(), messageSourceService.getMessage("apikey.not.registered"));

		String apiUrl = bithumbApiDetails.getBalance();
		try {
			Map<String, String> params = Collections.singletonMap("currency", cryptoCurrency.name());
			String result = fetchResultFromPrivateApi(apiUrl, apiKey, params);
			BithumbBalance bithumbBalance = jacksonJsonUtils.readValue(result, BithumbBalance.class);
			bithumbBalance.setCryptoCurrency(cryptoCurrency);
			return bithumbBalance;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return new BithumbBalance("5900", cryptoCurrency, Collections.emptyMap());
	}

	public BithumbMyAssets getMyAssetsByUserId(long userId) {
		BithumbMyAssets bithumbMyAssets = new BithumbMyAssets();
		List<CryptoCurrency> cryptoCurrencies = CryptoCurrency.getAllCurrencies();
		cryptoCurrencies.stream().forEach(cryptoCurrency -> {
			BithumbBalance bithumbBalance = getBalanceByUserId(userId, cryptoCurrency);
			if (!"0000".equals(bithumbBalance.getStatus())) {
				return;
			}
			bithumbBalance.setCryptoCurrency(cryptoCurrency);
			bithumbMyAssets.add(bithumbBalance);
		});

		if (bithumbMyAssets.isEmpty()) {
			bithumbMyAssets.setSuccess(false);
			return bithumbMyAssets;
		}
		bithumbMyAssets.setSuccess(true);
		BithumbBalance first = bithumbMyAssets.first();
		bithumbMyAssets.setTotalKrw(first.getTotalKrw());
		bithumbMyAssets.setAvailableKrw(first.getAvailableKrw());
		bithumbMyAssets.setUsingKrw(first.getUsingKrw());
		return bithumbMyAssets;
	}
}
