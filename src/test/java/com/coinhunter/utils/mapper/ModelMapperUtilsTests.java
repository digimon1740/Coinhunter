package com.coinhunter.utils.mapper;

import com.coinhunter.core.dto.user.UserDto;
import com.coinhunter.core.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ModelMapperUtilsTests {

	@Autowired
	ModelMapperUtils modelMapperUtils;

	@Test
	public void convertToDtoTest() {
		User user = new User();
		user.setName("Tony");
		UserDto userDto = modelMapperUtils.convertToDto(user, UserDto.class);
		Assert.assertNotNull(userDto);
		Assert.assertEquals(userDto.getName(), "Tony");
		log.info("userDto.getName : {}", userDto.getName());
	}

	@Test
	public void convertToEntityTest() {
		UserDto userDto = new UserDto();
		userDto.setName("Tony");
		User user = modelMapperUtils.convertToEntity(userDto, User.class);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getName(), "Tony");
		log.info("user.getName : {}", user.getName());

	}
}
