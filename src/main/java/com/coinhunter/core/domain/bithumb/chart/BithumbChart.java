package com.coinhunter.core.domain.bithumb.chart;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class BithumbChart {

	private boolean success;

	private CryptoCurrency cryptoCurrency;

	public String data;

	private BithumbChart() {
	}
}
