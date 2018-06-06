package com.coinhunter.client.bithumb;

import com.coinhunter.model.value.CryptoCurrency;
import com.coinhunter.model.value.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiClientTests {

    @Value("${bithumb.base.url}")
    private String apiUrl;

    @Value("${bithumb.api.key}")
    private String apikey;

    @Value("${bithumb.secret.key}")
    private String apiSecret;

    @Value("${bithumb.market.list.url}")
    private String marketListUrl;

    @Value("${bithumb.account.url}")
    private String accountUrl;

    @Value("${bithumb.balance.url}")
    private String balanceUrl;

    private String fetchResultFromApi(String url, HashMap<String, String> params) {
        BithumbApiClient api = new BithumbApiClient(apiUrl, apikey, apiSecret);
        String result = null;
        try {
            result = api.callApi(url, params);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return result;
    }

    @Test
    public void getMarketListTest() {
        Map<String, Object> map = new HashMap<>();

        map.put("currency", CryptoCurrency.EOS.name());
        StringSubstitutor sub = new StringSubstitutor(map);
        marketListUrl = marketListUrl.replace("!", "$");
        String resolvedURL = sub.replace(marketListUrl);

        try {
            String result = fetchResultFromApi(resolvedURL, null);
            Assert.assertNotNull(result);
            log.info("result market : {}", result);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Test
    public void getAccountTest() {
        try {
            String result = fetchResultFromApi(accountUrl, null);
            Assert.assertNotNull(result);
            log.info("result account: {}", result);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Test
    public void getBalanceTest() {
        HashMap<String, String> rgParams = new HashMap<String, String>();
        rgParams.put("order_currency", CryptoCurrency.EOS.name());
        rgParams.put("payment_currency", CurrencyType.KRW.name());

        try {
            String result = fetchResultFromApi(balanceUrl, rgParams);
            Assert.assertNotNull(result);
            log.info("result balance : {}", result);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }
}
