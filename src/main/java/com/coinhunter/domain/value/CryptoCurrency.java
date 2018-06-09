package com.coinhunter.domain.value;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public enum CryptoCurrency {

	BTC, ETH, EOS, TRX, QTUM, GNT, ICX, ETC, MCO, BCH, BTG,
	LTC, MITH, OMG, XRP, ELF, ZEC, VEN, XMR, DASH, KNC, HSR;

	public static List<CryptoCurrency> getAllCurrencies() {
		return CollectionUtils.arrayToList(CryptoCurrency.values());
	}

	public static CryptoCurrency of(String currencyType) {
		if (StringUtils.isEmpty(currencyType)) {
			return BTC;
		}
		currencyType = currencyType.toUpperCase();
		return CryptoCurrency.valueOf(currencyType);
	}
}
