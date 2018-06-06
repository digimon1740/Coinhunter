package com.coinhunter.web.user;

import com.coinhunter.dto.user.UserDto;
import com.coinhunter.model.user.User;
import com.coinhunter.service.MessageSourceService;
import com.coinhunter.service.user.UserService;
import com.coinhunter.utils.mapper.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	private MessageSourceService messageSourceService;

	private ModelMapperUtils modelMapperUtils;

	@Autowired
	public UserController(UserService userService,
	                      MessageSourceService messageSourceService,
	                      ModelMapperUtils modelMapperUtils) {
		this.userService = userService;
		this.messageSourceService = messageSourceService;
		this.modelMapperUtils = modelMapperUtils;
	}

	@PostMapping(value = "/register")
	UserDto register(@RequestBody UserDto userDto) throws Exception {
		User user = modelMapperUtils.convertToEntity(userDto, User.class);
		userService.register(user);
		return userDto;
	}
}
