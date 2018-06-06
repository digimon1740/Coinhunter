package com.coinhunter.domain.watch;

import com.coinhunter.domain.user.ApiKey;
import com.coinhunter.domain.user.User;
import com.coinhunter.domain.value.CryptoCurrency;
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
@Table(name = "watch_bot_list")
@Entity
@JsonIgnoreProperties(value = {"id", "user", "apiKey"})
public class WatchBot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "api_key_id")
	private ApiKey apiKey;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "coin_type")
	@Enumerated(EnumType.STRING)
	private CryptoCurrency coinType;

	@NotNull
	@Column(name = "term")
	private int term;

	@Column(name = "auto_trading")
	private boolean autoTrading;

	@Column(name = "notified")
	private boolean notified;

	@NotNull
	@Column(name = "position")
	@Enumerated(EnumType.STRING)
	private WatchPosition position;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "last_modified_time")
	private LocalDateTime lastModifiedTime;

	@Column(name = "reg_time")
	private LocalDateTime regTime;
}
