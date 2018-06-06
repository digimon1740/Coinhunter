package com.coinhunter.domain.strategy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrailingStop {

	// 봇 시작 후 직전매수 금액의 +%돌파 후 최고가 대비 % 하락시 매도
	private float maxRate;

	private float downRate;

	private boolean enabled;
}
