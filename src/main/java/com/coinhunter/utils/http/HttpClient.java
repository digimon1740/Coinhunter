package com.coinhunter.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public class HttpClient {

	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

	public HttpClient() {
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.add("Accept", "*/*");
	}

	public HttpClient(HttpHeaders headers) {
		this.rest = new RestTemplate();
		this.headers = headers;
		this.headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		this.headers.add("Accept", "*/*");
	}

	public String get(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public String get(String uri, String params) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(params, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public String post(String uri, Map<String, String> body) {
		HttpEntity requestEntity = new HttpEntity(body, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}