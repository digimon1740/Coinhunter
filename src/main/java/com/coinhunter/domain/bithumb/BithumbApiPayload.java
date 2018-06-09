package com.coinhunter.domain.bithumb;

import com.coinhunter.domain.value.CryptoCurrency;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BithumbApiPayload {

	private long id;

	private CryptoCurrency cryptoCurrency;
}
