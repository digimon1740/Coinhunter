package com.coinhunter.core.repository.user;

import com.coinhunter.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);

	User findByEmail(String email);

	User findByNameAndEmail(String name, String email);
}
