package com.coinhunter.core.repository.api;

import com.coinhunter.core.domain.user.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {

	ApiKey findByUserId(long userId);
}
