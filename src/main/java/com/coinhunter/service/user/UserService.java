package com.coinhunter.service.user;

import com.coinhunter.exception.ResourceExistsException;
import com.coinhunter.domain.user.User;
import com.coinhunter.repository.UserRepository;
import com.coinhunter.service.MessageSourceService;
import com.coinhunter.utils.common.EmailValidator;
import com.coinhunter.utils.text.RandomStringMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserService {

	private UserRepository userRepository;

	private MessageSourceService messageSourceService;

	@Autowired
	public UserService(UserRepository userRepository, MessageSourceService messageSourceService) {
		this.userRepository = userRepository;
		this.messageSourceService = messageSourceService;
	}

	@Transactional(readOnly = true)
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	private boolean userExists(String name, String email) {
		return findByName(name) != null || findByEmail(email) != null;
	}

	@Transactional
	public User register(User user) {
		Assert.hasText(user.getName(), messageSourceService.getMessage("user.name.invalid"));
		Assert.hasText(user.getPassword(), messageSourceService.getMessage("user.password.invalid"));
		Assert.hasText(user.getEmail(), messageSourceService.getMessage("user.email.invalid"));
		Assert.isTrue(EmailValidator.isValid(user.getEmail()), messageSourceService.getMessage("user.email.invalid"));

		if (userExists(user.getName(), user.getEmail())) {
			throw new ResourceExistsException(messageSourceService.getMessage("user.exists"));
		}
		return userRepository.save(user);
	}

	@Transactional
	public String resetPassword(String name) {
		Assert.hasText(name, messageSourceService.getMessage("user.name.invalid"));

		User user = findByName(name);
		if (user == null) {
			throw new ResourceExistsException(messageSourceService.getMessage("user.not.exists"));
		}
		int length = 8; // 패스워드 길이 기본 정책
		String generated = RandomStringMaker.generate('0', 'z', length);
		user.setPassword(generated);
		return generated;
	}
}
