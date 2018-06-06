package com.coinhunter.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpClient {

	private String server;
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

	private HttpClient() {
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.add("Accept", "*/*");
	}

	public String get(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		//log.info("uri : {}",server + uri);
		ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return this.server;
	}
}