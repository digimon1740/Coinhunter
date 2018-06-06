package com.coinhunter.repository;

import com.coinhunter.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);

	User findByEmail(String email);
}
