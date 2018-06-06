package com.coinhunter.utils.crypt;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeUtils {

	// Encryte Password with BCryptPasswordEncoder
	public static String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}
