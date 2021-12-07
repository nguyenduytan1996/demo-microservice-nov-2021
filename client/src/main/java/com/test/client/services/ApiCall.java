package com.test.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ApiCall {
    @Autowired
    RestTemplate restTemplate;

    // private static String serviceBaseURL = "localhost:8090/service";
    private final WebClient.Builder loadBalancedWebClientBuilder;

    public ApiCall(WebClient.Builder webClientBuilder,
            ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
    }

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
}