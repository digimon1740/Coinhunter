package com.coinhunter.dto.user;

import com.coinhunter.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {

	private String name;

	private String password;

	private String email;

	private boolean emailAccepted;

	private String cellphone;

	private boolean approved;

	private Role role = Role.ROLE_USER;

	private LocalDateTime lastLoginTime;

	private LocalDateTime regTime;
}
