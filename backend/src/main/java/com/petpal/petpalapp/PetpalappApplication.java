package com.petpal.petpalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetpalappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetpalappApplication.class, args);
	}

}
