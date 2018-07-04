package com.coinhunter.core.domain.bithumb.transaction.histories;

public enum BithumbTransactionType {

	ASK/*판매*/, BID/*구매*/;

	public static BithumbTransactionType of(String type) {
		if ("ask".equalsIgnoreCase(type)) {
			return BithumbTransactionType.ASK;
		}
		return BithumbTransactionType.BID;
	}
}
