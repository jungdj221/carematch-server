package com.corp.carematch_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.corp.carematch_server.domain.user.repo")
public class CarematchServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarematchServerApplication.class, args);
	}

}
