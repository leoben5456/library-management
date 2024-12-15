package com.example.livreservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LiverConfig {
@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
