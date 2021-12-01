package com.test.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ApiCall {
    @Autowired
    RestTemplate restTemplate;

    // private static String serviceBaseURL = "localhost:8090/service";

    public String getService(String apiLink) {
        String url = "http://localhost:9001" + apiLink;
        String rs = restTemplate.getForObject(url, String.class);
        return rs;
    }
}