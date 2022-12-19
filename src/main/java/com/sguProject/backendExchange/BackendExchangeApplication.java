package com.sguProject.backendExchange;

import com.sguProject.backendExchange.services.initBalance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendExchangeApplication.class, args);
		//initBalance init = new initBalance();
		//init.init();
	}

}
