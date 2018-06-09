package com.coinhunter.service.api;

import com.coinhunter.domain.user.ApiKey;
import com.coinhunter.repository.api.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyService {

	private ApiKeyRepository apiKeyRepository;

	@Autowired
	public ApiKeyService(ApiKeyRepository apiKeyRepository) {
		this.apiKeyRepository = apiKeyRepository;
	}

	@Transactional(readOnly = true)
	public ApiKey findByUserId(long userId) {
		return apiKeyRepository.findByUserId(userId);
	}
}
