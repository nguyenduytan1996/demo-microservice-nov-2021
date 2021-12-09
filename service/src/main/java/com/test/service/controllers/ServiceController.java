package com.test.service.controllers;

import com.test.service.Services.ServiceOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/servicecontroller")
public class ServiceController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	@Autowired
	ServiceOne serviceOne;

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

	@GetMapping("/demo3")
	private String demo3() {
		logger.info("/demo3 Controller");
		serviceOne.callServiceTracer();
		return "Service demo - 2 - method ";
	}

}
