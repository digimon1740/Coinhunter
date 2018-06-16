
package com.coinhunter.core.domain.converter;

import com.coinhunter.utils.crypto.pbe.PBECipherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class PBECipherConverter implements AttributeConverter<String, String> {

	private static final String PASSWORD = "dkagh!@#";

	@Override
	public String convertToDatabaseColumn(String sensitive) {
		if (StringUtils.isEmpty(sensitive)) {
			return null;
		}
		return PBECipherUtils.encryptText(PASSWORD, sensitive);
	}

	@Override
	public String convertToEntityAttribute(String sensitive) {
		if (StringUtils.isEmpty(sensitive)) {
			return null;
		}
		return PBECipherUtils.decryptText(PASSWORD, sensitive);
	}
}
