package com.coinhunter.core.dto.user;

import com.coinhunter.core.domain.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {

	private long id;

	private String name;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String email;

	private boolean emailAccepted;

	private String cellphone;

	private boolean approved;

	private Role role = Role.ROLE_USER;

	private LocalDateTime lastLoginTime;
}
