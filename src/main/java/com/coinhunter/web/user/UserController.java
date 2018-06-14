package com.coinhunter.web.user;

import com.coinhunter.core.domain.user.User;
import com.coinhunter.core.dto.user.UserDto;
import com.coinhunter.core.service.message.MessageSourceService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import com.coinhunter.core.service.user.UserService;
import com.coinhunter.utils.mapper.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	private UserDetailsServiceImpl userDetailsService;

	private MessageSourceService messageSourceService;

	private ModelMapperUtils modelMapperUtils;

	@Autowired
	public UserController(UserService userService,
	                      UserDetailsServiceImpl userDetailsService,
	                      MessageSourceService messageSourceService,
	                      ModelMapperUtils modelMapperUtils) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.messageSourceService = messageSourceService;
		this.modelMapperUtils = modelMapperUtils;
	}

	@GetMapping(value = "/details")
	UserDto findUserDetails() {
		User user = userService.findByName(userDetailsService.getUsername());
		return modelMapperUtils.convertToDto(user, UserDto.class);
	}

	@PostMapping(value = "/register")
	UserDto register(@RequestBody UserDto userDto) {
		User user = modelMapperUtils.convertToEntity(userDto, User.class);
		userService.register(user);
		return userDto;
	}

	@PutMapping(value = "/reset-password")
	String resetPassword(@RequestParam("name") String name, @RequestParam("email") String email) {
		return userService.resetPassword(name, email);
	}
}
