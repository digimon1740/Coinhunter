package com.coinhunter.core.domain.bithumb.transaction.histories;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.MapUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * status	결과 상태 코드 (정상 : 0000, 정상이외 코드는 에러 코드 참조)
 * cont_no	체결번호
 * transaction_date	거래 채결 시간
 * type	판/구매 (ask, bid)
 * units_traded	거래 Currency 수량
 * price	1Currency 거래 금액
 * total	총 거래금액
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BithumbTransactionHistory {

	private CryptoCurrency cryptoCurrency;

	private BithumbTransactionType bithumbTransactionType;

	private long countNo;

	private long price;

	private long total;

	private double tradedUnits;

	private LocalDateTime transactionDate;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	void setBithumbTransactionType(Map<String, Object> data) {
		this.bithumbTransactionType = BithumbTransactionType.of(MapUtils.getString(data, "type"));
	}

	void setCountNo(Map<String, Object> data) {
		this.countNo = MapUtils.getLongValue(data, "cont_no", 0);
	}

	void setPrice(Map<String, Object> data) {
		this.price = MapUtils.getLongValue(data, "price", 0);
	}

	void setTotal(Map<String, Object> data) {
		this.total = MapUtils.getLongValue(data, "total", 0);
	}

	void setTradedUnits(Map<String, Object> data) {
		this.tradedUnits = MapUtils.getDoubleValue(data, "units_traded", 0);
	}

	void setTransactionDate(Map<String, Object> data) {
		String transactionDate = MapUtils.getString(data, "transaction_date");
		this.transactionDate = LocalDateTime.parse(transactionDate, DATE_TIME_FORMATTER);
	}
}