package com.coinhunter.model.user;

import org.springframework.util.StringUtils;

public enum Role {
	ROLE_USER, ROLE_ADMIN;

	public static Role of(String name) {
		if (StringUtils.isEmpty(name))
			return Role.ROLE_USER;
		name = name.trim();
		if (ROLE_ADMIN.name().equalsIgnoreCase(name))
			return Role.ROLE_ADMIN;
		return Role.ROLE_USER;
	}

}
