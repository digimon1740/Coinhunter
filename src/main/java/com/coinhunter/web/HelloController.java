package com.coinhunter.web;

import com.coinhunter.core.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	private MessageSourceService messageSourceService;

	@Autowired
	public HelloController(
		MessageSourceService messageSourceService) {
		this.messageSourceService = messageSourceService;
	}

	@GetMapping({"/", "/login"})
	public String welcome() {
		return "redirect:hello.html";
	}

	@GetMapping(value = "/main")
	public String main() {
		return "redirect:main.html";
	}
}
