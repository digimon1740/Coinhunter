package com.coinhunter.model.value;

import org.springframework.util.CollectionUtils;

import java.util.List;

public enum CryptoCurrency {

	BTC, ETH, EOS, TRX, QTUM, GNT, ICX, ETC, MCO, BCH, BTG,
	LTC, MITH, OMG, XRP, ELF, ZEC, VEN, XMR, DASH, KNC, HSR;

	public static List<CryptoCurrency> getAllCurrencies() {
		return CollectionUtils.arrayToList(CryptoCurrency.values());
	}
}
