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

	@GetMapping("/")
	private String callService() {
		InstanceInfo instance = client.getNextServerFromEureka("service", false);
		String url = instance.getHomePageUrl();

		RestTemplate restTemplate = templateBuilder.build();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		return response.getBody();
	}

	@GetMapping("/callService")
	private ResponseEntity<String> callService2() {
		// ResponseEntity<String> responseEntity = new
		// RestTemplate().getForEntity("http://localhost:9001/demo2", String.class);
		// ResponseEntity<String> responseEntity = new
		// RestTemplate().getForEntity("http://service/demo2", String.class);
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:9001/demo2",
				String.class);
		return responseEntity;
	}

	@GetMapping("/sayhi")
	private String sayhi() {
		return "Hello from Client";
	}
}
