package com.coinhunter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(BaseConfig.class)
@Configuration
@Profile("live")
@EnableJpaRepositories(basePackages = "com.coinhunter.core.repository")
public class AppConfig {
}
