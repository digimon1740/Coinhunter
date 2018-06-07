package com.coinhunter.service.user;

import com.coinhunter.exception.AccessDeniedException;
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
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, AccessDeniedException {
		com.coinhunter.domain.user.User user = userService.findByName(userName);
		if (user == null) {
			log.info("User not found :  {}", userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}

		if (!user.isApproved()) {
			log.info("User is not approved :  {}", userName);
			throw new AccessDeniedException("User " + userName + " has not been approved by admin");
		}

		UserDetails userDetails = null;
		try {
			List<GrantedAuthority> grantList = new ArrayList<>();
			GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
			grantList.add(authority);

			userDetails = new User(user.getName(), user.getPassword(), grantList);
			userService.stampLoginTime(user.getName());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return userDetails;
	}
}