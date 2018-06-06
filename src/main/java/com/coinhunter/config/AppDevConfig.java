package com.coinhunter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(BaseConfig.class)
@Configuration
@Profile("dev")
@EnableJpaRepositories(basePackages = "com.coinhunter.repository")
public class AppDevConfig {
}