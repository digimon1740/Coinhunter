package com.coinhunter.core.domain.user;

import com.coinhunter.core.domain.converter.PBECipherConverter;
import com.coinhunter.core.domain.value.Exchange;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "api_keys")
@Entity
@JsonIgnoreProperties(value = {"id", "user"})
public class ApiKey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
	@ManyToOne
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
	@Convert(converter = PBECipherConverter.class)
	private String accessKey;

	@NotNull
	@Column(name = "secret_key")
	@Convert(converter = PBECipherConverter.class)
	private String secretKey;

	@Column(name = "last_modified_time")
	private LocalDateTime lastModifiedTime;

	@Column(name = "reg_time")
	private LocalDateTime regTime;
}
