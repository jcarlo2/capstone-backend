package com.capstone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.capstone.backend.repository")
public class CapstoneBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneBackendApplication.class, args);

    }

}
