package com.coinhunter.web.user;

import com.coinhunter.dto.user.UserDto;
import com.coinhunter.model.user.User;
import com.coinhunter.service.MessageSourceService;
import com.coinhunter.service.user.UserService;
import com.coinhunter.utils.mapper.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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

	@GetMapping(value = "{name}")
	UserDto findOne(@PathVariable("name") String name) {
		User user = userService.findByName(name);
		return modelMapperUtils.convertToDto(user, UserDto.class);
	}

	@PostMapping(value = "/register")
	UserDto register(@RequestBody UserDto userDto) {
		User user = modelMapperUtils.convertToEntity(userDto, User.class);
		userService.register(user);
		return userDto;
	}
}
