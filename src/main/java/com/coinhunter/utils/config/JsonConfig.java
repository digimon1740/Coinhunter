package com.coinhunter.utils.config;

import com.coinhunter.core.domain.bithumb.BithumbApiDetails;
import com.coinhunter.utils.resource.ResourceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.InputStream;

@Slf4j
@Component
public class JsonConfig {

	private ObjectMapper om;

	private BithumbApiDetails bithumbApiDetails;

	@Autowired
	private JsonConfig(ObjectMapper om) {
		try (InputStream is = ResourceUtils.getInputStreamByFilePath("apis/bithumb.json")) {
			bithumbApiDetails = om.readValue(new BufferedInputStream(is), BithumbApiDetails.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public BithumbApiDetails getBithumbApiDetails() {
		return this.bithumbApiDetails;
	}
}
