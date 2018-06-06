package com.coinhunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CoinhunterApplication {

	//https://o7planning.org/en/11705/create-a-login-application-with-spring-boot-spring-security-jpa
	public static void main(String[] args) {
		SpringApplication.run(CoinhunterApplication.class, args);
	}
}
