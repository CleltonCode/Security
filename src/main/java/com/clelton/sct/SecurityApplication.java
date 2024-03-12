package com.clelton.sct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
public class SecurityApplication {
   
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}