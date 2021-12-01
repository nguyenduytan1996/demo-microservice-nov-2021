package com.test.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
// @RequestMapping("/clientcontroller")
public class ClientController {

	@Autowired
	private EurekaClient client;

	@Autowired
	private RestTemplateBuilder templateBuilder;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/")
	private String callService() {
		InstanceInfo instance = client.getNextServerFromEureka("service", false);
		String url = instance.getHomePageUrl();

		RestTemplate restTemplate = templateBuilder.build();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		return response.getBody();
	}

	@GetMapping("/callService1")
	private ResponseEntity<String> callService1() {
		ResponseEntity<String> rsEntity = new RestTemplate().getForEntity("http://localhost:9001/demo2", String.class);
		return rsEntity;
	}

	@GetMapping("/callService2")
	private String callService2() {
		String rsEntity = restTemplate.getForObject("http://service/demo2", String.class);
		return rsEntity;
	}

	@GetMapping("/sayhi")
	private String sayhi() {
		return "Hello from Client";
	}
}