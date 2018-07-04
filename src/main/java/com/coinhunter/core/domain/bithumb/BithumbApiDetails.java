package com.coinhunter.core.domain.bithumb;

import lombok.Data;

@Data
public class BithumbApiDetails {

	private String baseUrl;

	//거래소 마지막 거래 정보 예: /public/ticker/BTC
	private String ticker;

	//호가 정보 예: /public/orderbook/BTC
	private String marketList;

	//거래소 거래 체결 완료 내역 예: /public/transaction_history/BTC
	private String transactionHistories;

	//계정 정보
	private String account;

	//사용가능한 잔액조회
	private String balance;

	private String chart;

}
