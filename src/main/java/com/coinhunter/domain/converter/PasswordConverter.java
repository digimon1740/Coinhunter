package com.coinhunter.domain.converter;

import com.coinhunter.utils.crypto.PasswordEncodeUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Component
public class PasswordConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String sensitive) {
		if (StringUtils.isEmpty(sensitive)) {
			return null;
		}
		return PasswordEncodeUtils.encodePassword(sensitive);
	}

	@Override
	public String convertToEntityAttribute(String sensitive) {
		return sensitive;
	}
}
