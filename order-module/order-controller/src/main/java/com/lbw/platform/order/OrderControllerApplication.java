package com.lbw.platform.order;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = "com.lbw.platform.*")
//@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@Slf4j
public class OrderControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderControllerApplication.class, args);
	}
	
	
	/***
	 * 要加public 才能单元测试
	 */
	@Test
	public void test(){
		log.debug("debug message");
        log.warn("warn message");
        log.info("info message");
        log.error("error message");
        log.trace("trace message");
	}
}
