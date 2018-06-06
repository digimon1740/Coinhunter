package com.coinhunter.model.value;

import org.springframework.util.CollectionUtils;

import java.util.List;

public enum Exchange {

	BITHUMB, COINONE;

	public static List<Exchange> getAllExchanges() {
		return CollectionUtils.arrayToList(Exchange.values());
	}
}
