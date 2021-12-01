package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayApplication.class, args);
	}

	/**
server.port=8090

eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka

spring.application.name=gateway

spring.cloud.gateway.discovery.locator.lower-case-service-id=true

spring.cloud.gateway.routes[0].id=service
spring.cloud.gateway.routes[0].uri=lb://service
spring.cloud.gateway.routes[0].predicates[0]=Path=/service/**

spring.cloud.gateway.routes[1].id=client
spring.cloud.gateway.routes[1].uri=lb://client
spring.cloud.gateway.routes[1].predicates[0]=Path=/client/**

#spring.cloud.loadbalancer.ribbon.enabled=false
#spring.cloud.gateway.discovery.locator.enabled=true
#spring.cloud.gateway.default-filters=TokenRelay

#spring.cloud.gateway.routes[1].filters[0]=RemoveRequestHeader=Cookie
#spring.cloud.gateway.routes[1].filters[0].name=Requestsize
#spring.cloud.gateway.routes[1].filters[0].args[Maxsize]=500000
	 */
}