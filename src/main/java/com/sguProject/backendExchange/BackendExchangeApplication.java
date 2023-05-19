package com.sguProject.backendExchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendExchangeApplication.class, args);
	}

}
