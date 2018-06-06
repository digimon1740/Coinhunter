package com.coinhunter.domain.strategy;

import com.coinhunter.domain.watch.WatchBot;
import com.coinhunter.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Table(name = "selling_strategies")
@Entity
@JsonIgnoreProperties(value = {"id", "user"})
public class SellingStrategy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "watch_bot_id")
	private WatchBot watchBot;

	@Column(name="strategy")
	private Strategy strategy;


}
