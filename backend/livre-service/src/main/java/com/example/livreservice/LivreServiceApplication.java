package com.example.livreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LivreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivreServiceApplication.class, args);
    }

}
