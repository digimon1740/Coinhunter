/**
 * @author SangHoon, Lee(devsh@helloworlds.co.kr)
 */
package com.coinhunter.utils.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JacksonJsonUtils {

	private ObjectMapper om;

	@Autowired
	private JacksonJsonUtils(ObjectMapper om) {
		this.om = om;
	}

	public <T> T readValue(String content, Class<T> valueType) throws Exception {
		T value = null;
		try {
			value = om.readValue(content, valueType);
		} catch (Exception e) {
			log.error("Error at JacksonJsonUtils.readValue({}, {}.class)", content, valueType.getName());
			throw e;
		}
		return value;
	}

	public String writeValueAsString(Object value) throws Exception {
		String json = null;
		try {
			json = om.writeValueAsString(value);
			if (StringUtils.isEmpty(json) || "null".equals(json))
				return "";
		} catch (Exception e) {
			log.error("Error at JacksonJsonUtils.writeValueAsString(Object)", e);
		}
		return json;
	}

}
