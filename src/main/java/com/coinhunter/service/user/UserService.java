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

import java.time.LocalDateTime;

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

	@Transactional(readOnly = true)
	public User findByNameAndEmail(String name, String email) {
		return userRepository.findByNameAndEmail(name, email);
	}

	@Transactional
	public User register(User user) {
		Assert.hasText(user.getName(), messageSourceService.getMessage("user.name.invalid"));
		Assert.hasText(user.getPassword(), messageSourceService.getMessage("user.password.invalid"));
		Assert.hasText(user.getEmail(), messageSourceService.getMessage("user.email.invalid"));
		Assert.isTrue(EmailValidator.isValid(user.getEmail()), messageSourceService.getMessage("user.email.invalid"));

		User exists = findByNameAndEmail(user.getName(), user.getEmail());
		if (exists != null) {
			throw new ResourceExistsException(messageSourceService.getMessage("user.exists"));
		}
		return userRepository.save(user);
	}

	@Transactional
	public String resetPassword(String name, String email) {
		Assert.hasText(name, messageSourceService.getMessage("user.name.invalid"));
		Assert.hasText(email, messageSourceService.getMessage("user.email.invalid"));
		Assert.isTrue(EmailValidator.isValid(email), messageSourceService.getMessage("user.email.invalid"));

		User user = findByNameAndEmail(name, email);
		if (user == null) {
			throw new ResourceExistsException(messageSourceService.getMessage("user.not.exists"));
		}
		int length = 8; // 패스워드 길이 기본 정책
		String generated = RandomStringMaker.generate('0', 'z', length);
		user.setPassword(generated);
		return generated;
	}

	@Transactional
	public LocalDateTime stampLoginTime(String name) {
		Assert.hasText(name, messageSourceService.getMessage("user.name.invalid"));
		User user = findByName(name);
		if (user == null) {
			throw new ResourceExistsException(messageSourceService.getMessage("user.not.exists"));
		}
		LocalDateTime now = LocalDateTime.now();
		user.setLastLoginTime(now);
		return now;
	}
}
