package com.coinhunter.domain.user;

import com.coinhunter.domain.value.Exchange;
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
@Table(name = "api_key")
@Entity
@JsonIgnoreProperties(value = {"id", "user"})
public class ApiKey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "exchange")
	@Enumerated(EnumType.STRING)
	private Exchange exchange;

	@NotNull
	@Column(name = "access_key")
	private String accessKey;

	@NotNull
	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "last_modified_time")
	private LocalDateTime lastModifiedTime;

	@Column(name = "reg_time")
	private LocalDateTime regTime;
}
