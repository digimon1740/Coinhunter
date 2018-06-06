package com.coinhunter.model.strategy;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class Strategy implements Serializable {

	private ProfitTarget profitTarget;

	private StopLoss stopLoss;

	private TrailingStop trailingStop;

}
