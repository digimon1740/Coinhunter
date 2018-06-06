package com.coinhunter.service.user;

import com.coinhunter.model.user.User;
import com.coinhunter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public User register(User user) {
		return userRepository.save(user);
	}
}
