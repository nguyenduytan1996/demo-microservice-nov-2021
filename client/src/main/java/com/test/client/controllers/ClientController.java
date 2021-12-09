package com.test.client.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import reactor.core.publisher.Mono;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import com.test.client.services.ApiCall;

@RestController
// @RequestMapping("/clientcontroller")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private EurekaClient client;

	@Autowired
	private RestTemplateBuilder templateBuilder;

	@Autowired
	private RestTemplate restTemplate2;

	@Autowired
	private ApiCall apiCall;

	// Call by spring.application.name
	@GetMapping("/")
	private String callService() {
		InstanceInfo instance = client.getNextServerFromEureka("service", false);
		String url = instance.getHomePageUrl();

		RestTemplate restTemplate = templateBuilder.build();
		ResponseEntity<String> response = restTemplate.exchange(url + "/demo2", HttpMethod.GET, null, String.class);
		return response.getBody();
	}

	// Call by URL project
	@GetMapping("/callService1")
	private ResponseEntity<String> callService1() {
		ResponseEntity<String> rsEntity = new RestTemplate().getForEntity("http://localhost:9001/demo", String.class);
		return rsEntity;
	}

	// Call by name: spring.application.name
	@GetMapping("/callService2")
	public String callService2() {
		String string = restTemplate2.getForObject("http://service/demo", String.class);
		return string;
	}

	// Call with balance
	@GetMapping("/callService3")
	public Mono<String> callService3() {
		logger.info("Client Controller - Call with balance");
		return apiCall.getServiceBalance();
	}

	// ****************************************************************************************
	@GetMapping("/callService4")
	@CircuitBreaker(name = "DEMO", fallbackMethod = "callFallback")
	public ResponseEntity<String> callService4() {
		String response = restTemplate2.getForObject("http://service/callNotExistNe", String.class);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> callFallback(Exception e) {
		return new ResponseEntity<String>("Item service/callNotExistNe is Khong Co", HttpStatus.OK);

	}

	// circuitBreakerDemo - Viet Lai Theo Cach Moi
	@GetMapping("/callService5")
	public ResponseEntity<String> callService5() {
		return new ResponseEntity<String>(apiCall.circuitBreakerDemo("http://service/callNotExistNe"), HttpStatus.OK);
	}

	// CIRCUIT BREAKER + LOAD BALANCE
	@GetMapping("/callService6")
	public ResponseEntity<String> callService6() {
		return new ResponseEntity<String>(apiCall.getServiceBalanceCircuitBreaker("http://service/demo9"),
				HttpStatus.OK);
	}

	@GetMapping("/sayhi")
	private String sayhi() {
		return "Hello from Client";
	}
}
