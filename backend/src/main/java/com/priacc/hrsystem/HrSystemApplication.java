package com.priacc.hrsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HrSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrSystemApplication.class, args);
	}

}