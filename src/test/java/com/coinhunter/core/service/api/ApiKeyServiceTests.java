package com.coinhunter.core.service.api;


import com.coinhunter.core.domain.user.ApiKey;
import com.coinhunter.core.service.api.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ApiKeyServiceTests {

	@Autowired
	private ApiKeyService apiKeyService;

	@Test
	public void findByUserIdTest() {
		ApiKey apiKey = apiKeyService.findByUserId(1);
		Assert.assertNotNull(apiKey);
		log.info("apiKey : {}", apiKey);

	}
}
