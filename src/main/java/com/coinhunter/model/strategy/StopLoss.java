package com.coinhunter.model.strategy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopLoss {

	//봇 시작 후 직전매수 금액의 +%이하일 경우 매도
	private float rate;

	private boolean enabled;
}
