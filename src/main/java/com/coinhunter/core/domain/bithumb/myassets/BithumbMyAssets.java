package com.coinhunter.core.domain.bithumb.myassets;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BithumbMyAssets {

	private boolean success;

	private long totalKrw;

	private long availableKrw;

	private long usingKrw;

	private List<BithumbBalance> myAssets = new ArrayList<>();

	public boolean add(BithumbBalance bithumbBalance) {
		return myAssets.add(bithumbBalance);
	}

	public BithumbBalance get(int i) {
		return myAssets.get(i);
	}

	public BithumbBalance first() {
		return get(0);
	}

	public BithumbBalance last() {
		return get(myAssets.size() - 1);
	}

	public int size() {
		return myAssets.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public void sortByCryptoAsset() {
		myAssets.sort(Comparator.comparing(BithumbBalance::getValue).reversed());
	}

	public long sumTotalKrw() {
		if (size() == 0)
			return 0;
		double sum = myAssets.stream()
			.map(myAsset -> myAsset.getTotalCryptoCurrency() * myAsset.getMarketPrice())
			.reduce(Double::sum).get();
		return (long) sum;
	}
}
