package com.coinhunter.core.domain.bithumb.myassets;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
