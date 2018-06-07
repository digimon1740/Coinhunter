package com.coinhunter.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		com.coinhunter.domain.user.User user = userService.findByName(userName);
		if (user == null) {
			log.info("User not found :  {}", userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}

		List<GrantedAuthority> grantList = new ArrayList<>();
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
		grantList.add(authority);

		UserDetails userDetails = new User(user.getName(), user.getPassword(), grantList);
		userService.stampLoginTime(user.getName());
		return userDetails;
	}
}