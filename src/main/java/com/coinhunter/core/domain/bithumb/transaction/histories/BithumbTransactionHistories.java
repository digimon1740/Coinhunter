package com.coinhunter.core.domain.bithumb.transaction.histories;

import com.coinhunter.core.domain.value.CryptoCurrency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BithumbTransactionHistories {

	private String status;

	private boolean success;

	private CryptoCurrency cryptoCurrency;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Map<String, Object>> data = new ArrayList<>();

	private List<BithumbTransactionHistory> askedHistories = new ArrayList<>();

	private List<BithumbTransactionHistory> bidHistories = new ArrayList<>();

	public boolean isSuccess() {
		return "0000".equals(status);
	}

	public void convertDataToTransactionHistories() {
		if (CollectionUtils.isEmpty(data)) {
			return;
		}
		data.stream().forEach(map -> {
			BithumbTransactionHistory bithumbTransactionHistory = new BithumbTransactionHistory();
			bithumbTransactionHistory.setCryptoCurrency(cryptoCurrency);
			bithumbTransactionHistory.setBithumbTransactionType(map);
			bithumbTransactionHistory.setCountNo(map);
			bithumbTransactionHistory.setPrice(map);
			bithumbTransactionHistory.setTotal(map);
			bithumbTransactionHistory.setTradedUnits(map);
			bithumbTransactionHistory.setTransactionDate(map);
			String type = bithumbTransactionHistory.getBithumbTransactionType().name();
			if (BithumbTransactionType.BID.name().equals(type)) {
				bidHistories.add(bithumbTransactionHistory);
			} else {
				askedHistories.add(bithumbTransactionHistory);
			}
		});
	}

	public void sortByCryptoAsset() {
		//bithumbTransactionHistories.sort(Comparator.comparing(BithumbTransactionHistory::getValue).reversed());
	}
}
