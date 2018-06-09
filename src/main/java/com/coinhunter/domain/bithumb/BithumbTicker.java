package com.coinhunter.domain.bithumb;

import lombok.*;
import org.apache.commons.collections.MapUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@NoArgsConstructor
public class BithumbTicker {

	private long userId;

	private String status;

	private Map<String, Object> data = new HashMap<>();

	public boolean connecionSucceded() {
		return "0000".equals(this.status);
	}

	public long getOpeningPrice() {
		return MapUtils.getLong(data, "opening_price");
	}

	public long getClosingPrice() {
		return MapUtils.getLong(data, "closing_price");
	}

	public long getMinPrice() {
		return MapUtils.getLong(data, "min_price");
	}

	public long getMaxPrice() {
		return MapUtils.getLong(data, "max_price");
	}

	public double getAveragePrice() {
		return MapUtils.getDouble(data, "average_price");
	}

	public double getUnitsTraded() {
		return MapUtils.getDouble(data, "units_traded");
	}

	public double getVolume1Day() {
		return MapUtils.getDouble(data, "volume_1day");
	}

	public double getVolume7Day() {
		return MapUtils.getDouble(data, "volume_7day");
	}

	public long getBuyPrice() {
		return MapUtils.getLong(data, "buy_price");
	}

	public long getSellPrice() {
		return MapUtils.getLong(data, "sell_price");
	}

	public long get24HFluctate() {
		return MapUtils.getLong(data, "24H_fluctate");
	}

	public double get24HFluctateRate() {
		return MapUtils.getDouble(data, "24H_fluctate_rate");
	}

	public LocalDateTime getDate() {
		Timestamp timestamp = new Timestamp(MapUtils.getShortValue(data, "date"));
		return timestamp.toLocalDateTime();
	}
}