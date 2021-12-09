package com.test.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@Service
public class ApiCall {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder loadBalancedWebClientBuilder;

    // Call a service
    public String getService(String apiLink) {
        String url = "http://localhost:9001" + apiLink;
        String rs = restTemplate.getForObject(url, String.class);
        return rs;
    }

    // Call a service with Balance
    public Mono<String> getServiceBalance() {
        return loadBalancedWebClientBuilder.build().get().uri("http://service/demo").retrieve()
                .bodyToMono(String.class);
    }

    // CIRCUIT BREAKER
    @CircuitBreaker(name = "DEMO", fallbackMethod = "callFallback")
    public String circuitBreakerDemo(String urlService) { // http://service/callNotExistNe
        String response = restTemplate.getForObject(urlService, String.class);
        return response;
    }

    public String callFallback(String urlService, Exception e) {
        return urlService + " Item service/callNotExistNe is Khong Co";
    }

    // CIRCUIT BREAKER + LOAD BALANCE
    @CircuitBreaker(name = "DEMO", fallbackMethod = "callFallback")
    public String getServiceBalanceCircuitBreaker(String urlService) {
        return loadBalancedWebClientBuilder.build().get().uri(urlService).retrieve()
                .bodyToMono(String.class).block();
    }

}