package com.test.service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServiceController {
	
	@Value("${service.instance.name}")
	private String instance;
	
	@RequestMapping("/")
	private String sayHello() {
		return "Hello from " + instance;
	}
}
