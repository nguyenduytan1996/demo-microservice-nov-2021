package com.test.service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/servicecontroller")
public class ServiceController {

	@Value("${service.instance.name}")
	private String instance;

	// @GetMapping("/sayhello")
	@GetMapping("/")
	private String sayHello() {
		return "Hello from " + instance;
	}

	@GetMapping("/demo")
	private String demo() {
		return "Service demo method";
	}

	@GetMapping("/demo2")
	private String demo2() {
		return "Service demo - 2 - method";
	}
}
