package com.coinhunter.domain.value;

import org.springframework.util.CollectionUtils;

import java.util.List;

public enum Exchange {

	BITHUMB, UPBIT, COINONE;

	public static List<Exchange> getAllExchanges() {
		return CollectionUtils.arrayToList(Exchange.values());
	}
}
