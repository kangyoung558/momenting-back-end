package com.momenting.momentingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DoaboutsomethingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoaboutsomethingApplication.class, args);
	}

}
