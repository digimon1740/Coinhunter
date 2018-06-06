package com.coinhunter.service;


import com.coinhunter.model.user.User;
import com.coinhunter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTests {

	@Autowired
	private UserService userService;

	@Test
	@Transactional
	public void registerTest() {
		User user = new User();
		user.setName("Tony");
		user.setPassword("tototo");
		user.setEmail("ddd@");
		userService.register(user);
	}
}