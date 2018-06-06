package com.coinhunter.utils.jackson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JacksonJsonUtilsTest {

	@Autowired
	JacksonJsonUtils jacksonJsonUtils;

	String response = "{\"name\":\"ddd\"}";

	@Test
	public void readValueTest() throws Exception {
		Response responseObj = jacksonJsonUtils.readValue(response, Response.class);
		Assert.assertNotNull(responseObj);
		Assert.assertEquals(responseObj.getName(), "ddd");
		log.info("response: {}", responseObj.getName());
	}
}

class Response {
	private String name;

	public String getName() {
		return this.name;
	}
}