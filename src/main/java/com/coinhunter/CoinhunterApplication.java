package com.coinhunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CoinhunterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinhunterApplication.class, args);
	}
}
