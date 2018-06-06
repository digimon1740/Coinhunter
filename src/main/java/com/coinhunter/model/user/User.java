package com.coinhunter.model.user;


import com.coinhunter.utils.common.EmailValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Table(name = "users")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "email_accepted")
	private boolean emailAccepted;

	@Column(name = "cellphone")
	private String cellphone;

	@Column(name = "approved")
	private boolean approved;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

	@Column(name = "last_login_time")
	private LocalDateTime lastLoginTime;

	@Column(name = "reg_time")
	private LocalDateTime regTime;

	public boolean emailValidated() {
		String email = this.email;
		if (StringUtils.isEmpty(email))
			return false;
		// TODO 대소문자형식 체크해야함
		return EmailValidator.isValid(email);
	}
}
