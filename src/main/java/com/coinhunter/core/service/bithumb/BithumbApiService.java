package com.coinhunter.core.service.bithumb;

import com.coinhunter.client.bithumb.BithumbApiClient;
import com.coinhunter.core.domain.bithumb.BithumbApiDetails;
import com.coinhunter.core.domain.bithumb.chart.BithumbChart;
import com.coinhunter.core.domain.bithumb.myassets.BithumbBalance;
import com.coinhunter.core.domain.bithumb.myassets.BithumbMyAssets;
import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.domain.bithumb.transaction.histories.BithumbTransactionHistories;
import com.coinhunter.core.domain.bithumb.transaction.histories.BithumbTransactionHistory;
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

	private String fetchResultFromPublicApi(String requestPath, Map<String, String> map) {
		String requestUrl = bithumbApiDetails.getBaseUrl() + requestPath;
		HttpClient client = new HttpClient();
		String params = BithumbClientUtils.mapToQueryString(map);
		return client.get(requestUrl, params);
	}

	public BithumbTicker getTickerByCryptoCurrency(CryptoCurrency cryptoCurrency) {
		String apiUrl = bithumbApiDetails.getTicker();
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

	public BithumbBalance getBalanceByUserId(ApiKey apiKey, CryptoCurrency cryptoCurrency) {
		Assert.notNull(apiKey, messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getAccessKey(), messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getSecretKey(), messageSourceService.getMessage("apikey.not.registered"));

		String apiUrl = bithumbApiDetails.getBalance();
		try {
			Map<String, String> params = Collections.singletonMap("currency", cryptoCurrency.name());
			String result = fetchResultFromPrivateApi(apiUrl, apiKey, params);
			BithumbBalance bithumbBalance = jacksonJsonUtils.readValue(result, BithumbBalance.class);
			bithumbBalance.setCryptoCurrency(cryptoCurrency);
			bithumbBalance.setDataProperties();
			return bithumbBalance;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return BithumbBalance.builder()
			.status("5900")
			.cryptoCurrency(cryptoCurrency)
			.data(Collections.emptyMap())
			.build();
	}

	public BithumbBalance getBalanceByUserId(long userId, CryptoCurrency cryptoCurrency) {
		ApiKey apiKey = apiKeyService.findByUserId(userId);
		Assert.notNull(apiKey, messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getAccessKey(), messageSourceService.getMessage("apikey.not.registered"));
		Assert.notNull(apiKey.getSecretKey(), messageSourceService.getMessage("apikey.not.registered"));
		return getBalanceByUserId(apiKey, cryptoCurrency);
	}

	public BithumbMyAssets getMyAssetsByUserId(long userId) {
		ApiKey apiKey = apiKeyService.findByUserId(userId);

		BithumbMyAssets bithumbMyAssets = new BithumbMyAssets();
		List<CryptoCurrency> cryptoCurrencies = CryptoCurrency.getAllCurrencies();
		cryptoCurrencies.stream().forEach(cryptoCurrency -> {
			BithumbBalance bithumbBalance = getBalanceByUserId(apiKey, cryptoCurrency);
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
		bithumbMyAssets.setTotalKrw(bithumbMyAssets.sumTotalKrw());
		BithumbBalance first = bithumbMyAssets.first();
		bithumbMyAssets.setAvailableKrw(first.getAvailableKrw());
		bithumbMyAssets.setUsingKrw(first.getUsingKrw());
		bithumbMyAssets.sortByCryptoAsset();
		return bithumbMyAssets;
	}

	public BithumbTransactionHistories getTransactionHistories(CryptoCurrency cryptoCurrency) {
		String apiUrl = bithumbApiDetails.getTransactionHistories();
		try {
			apiUrl = apiUrl.replace("{cryptoCurrency}", cryptoCurrency.name());
			String result = fetchResultFromPublicApi(apiUrl, null);
			BithumbTransactionHistories bithumbTransactionHistories = jacksonJsonUtils.readValue(result, BithumbTransactionHistories.class);
			bithumbTransactionHistories.setCryptoCurrency(cryptoCurrency);
			bithumbTransactionHistories.convertDataToTransactionHistories();
			return bithumbTransactionHistories;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return BithumbTransactionHistories.builder()
			.status("5900")
			.cryptoCurrency(cryptoCurrency)
			.data(Collections.emptyList())
			.build();
	}
}
