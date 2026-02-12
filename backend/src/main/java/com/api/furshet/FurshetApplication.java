package com.api.furshet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FurshetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurshetApplication.class, args);
	}

}
