package com.coinhunter.repository.api;

import com.coinhunter.domain.user.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {

	ApiKey findByUserId(long userId);
}
