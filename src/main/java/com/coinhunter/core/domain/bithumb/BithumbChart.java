package com.coinhunter.core.domain.bithumb;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BithumbChart {

	private boolean success;

	private CryptoCurrency cryptoCurrency;

	public String data;
}
