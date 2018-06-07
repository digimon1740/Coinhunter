package com.coinhunter.config;

import com.coinhunter.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsService;

	private DataSource dataSource;

	@Autowired
	public WebSecurityConfig(
		UserDetailsServiceImpl userDetailsService, DataSource dataSource) {
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "*//**")
			.antMatchers("/assets*//**")
			.antMatchers("/css*//**")
			.antMatchers("/fonts*//**")
			.antMatchers("/img*//**")
			.antMatchers("/js*//**")
			.antMatchers("/hello.html")
			.antMatchers("/register.html")
			.antMatchers("/forgot-password.html")
			.antMatchers("/robots.txt");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// The pages does not require login
		http.authorizeRequests().antMatchers("/", "/login", "/logout", "/users/register", "/users/reset-password").permitAll();

		// For ADMIN only.
		http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

		// /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
		// If no login, it will redirect to /login page.
		http.authorizeRequests().anyRequest().access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// When the user has logged in as XX.
		// But access a page that requires role YY,
		// AccessDeniedException will be thrown.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Config for Login Form
		http.authorizeRequests().and().formLogin()//
			// Submit URL of login page.
			.loginProcessingUrl("/j_spring_security_check") // Submit URL
			.loginPage("/login")//
			.defaultSuccessUrl("/main", true)
			.failureUrl("/login?error=true")//
			.usernameParameter("username")//
			.passwordParameter("password")
			// Config for Logout Page
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/my-error-page");
	}
}
