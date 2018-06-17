package com.coinhunter.core.domain.value;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public enum CryptoCurrency {

	BTC, ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM, BTG, EOS, ICX, VEN, TRX, ELF,
	MITH, MCO, OMG, KNC, GNT, HSR, ZIL, ETHOS, PAY, WAX, POWR, LRC, GTO, STEEM, STRAT, ZRX, REP, AE, XEM, SNT, ADA;

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
