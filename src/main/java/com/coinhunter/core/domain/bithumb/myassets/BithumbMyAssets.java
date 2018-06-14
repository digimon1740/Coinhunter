package com.coinhunter.core.domain.bithumb.myassets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BithumbMyAssets {

	private String status;

	private List<BithumbBalance> myAssets = new ArrayList<>();

	public boolean add(BithumbBalance bithumbBalance) {
		return myAssets.add(bithumbBalance);
	}

	public BithumbBalance get(int i) {
		return myAssets.get(i);
	}

	public int size() {
		return myAssets.size();
	}
}
