package com.pastamania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PastamaniaServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PastamaniaServiceApplication.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		SpringApplication.run(PastamaniaServiceApplication.class, args);
	}

}
