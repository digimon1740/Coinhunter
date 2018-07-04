package com.coinhunter.core.domain.bithumb.ticker;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.*;
import java.util.HashMap;
import java.util.Map;

/**
 * status	결과 상태 코드 (정상 : 0000, 정상이외 코드는 에러 코드 참조)
 * opening_price	최근 24시간 내 시작 거래금액
 * closing_price	최근 24시간 내 마지막 거래금액
 * min_price	최근 24시간 내 최저 거래금액
 * max_price	최근 24시간 내 최고 거래금액
 * average_price	최근 24시간 내 평균 거래금액
 * units_traded	최근 24시간 내 Currency 거래량
 * volume_1day	최근 1일간 Currency 거래량
 * volume_7day	최근 7일간 Currency 거래량
 * buy_price	거래 대기건 최고 구매가
 * sell_price	거래 대기건 최소 판매가
 * 24H_fluctate	24시간 변동금액
 * 24H_fluctate_rate	24시간 변동률
 * date	현재 시간 Timestamp
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BithumbTicker {

	private String status;

	private CryptoCurrency cryptoCurrency;

	private Map<String, Object> data = new HashMap<>();

	private BithumbTicker() {
	}
}