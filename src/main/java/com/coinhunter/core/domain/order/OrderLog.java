package com.coinhunter.core.domain.order;

import com.coinhunter.core.domain.strategy.Strategy;
import com.coinhunter.core.domain.watch.WatchBot;
import com.coinhunter.core.domain.user.User;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Table(name = "order_logs")
@Entity
@JsonIgnoreProperties(value = {"id", "user"})
public class OrderLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "watch_id")
	private WatchBot watchBot;

	@NotNull
	@Column(name = "coin_type")
	@Enumerated(EnumType.STRING)
	private CryptoCurrency coinType;

	@NotNull
	@Column(name = "order_method")
	@Enumerated(EnumType.STRING)
	private OrderMethod orderMethod;

	@Column(name="strategy")
	private Strategy strategy;

	@NotNull
	@Column(name = "quantity")
	private double quantity;

	@NotNull
	@Column(name = "avg_price")
	private long avgPrice;

	@NotNull
	@Column(name = "trade_price")
	private long tradePrice;

	@NotNull
	@Column(name = "income_fee")
	private long incomeFee;

	@Column(name = "reg_time")
	private LocalDateTime regTime;
}
