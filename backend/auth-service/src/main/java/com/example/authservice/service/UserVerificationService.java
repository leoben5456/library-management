package com.example.authservice.service;


import com.example.authservice.DTO.LoginRequest;
import com.example.authservice.DTO.UserDTO;
import com.example.authservice.exception.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

import java.rmi.ServerException;

@Service
public class UserVerificationService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(UserVerificationService.class);
    public UserVerificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/user-service")
                .build();
    }

    public Mono<UserDTO> verifyUser(String email, String password) {
        logger.info("Attempting to verify user with email: {}", email);

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        return webClient.post()
                .uri("/verify-credentials")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    logger.warn("Client error occurred while verifying user with email: {}", email);
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                logger.error("Client error details: {}", errorBody);
                                return Mono.error(new ClientException("Invalid credentials: " + errorBody));
                            });
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("Server error occurred while verifying user with email: {}", email);
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                logger.error("Server error details: {}", errorBody);
                                return Mono.error(new ServerException("Server error: " + errorBody));
                            });
                })
                .bodyToMono(UserDTO.class)
                .doOnSuccess(userDTO -> logger.info("User verification successful for email: {}", email))
                .onErrorResume(e -> {
                    if (e instanceof ClientException || e instanceof ServerException) {
                        logger.error("Error during user verification: {}", e.getMessage(), e);
                        return Mono.error(e);
                    }
                    logger.error("Unexpected error occurred during user verification", e);
                    return Mono.error(new RuntimeException("Unexpected error occurred", e));
                });
    }


}
