package com.example.authservice.service;


import com.example.authservice.DTO.LoginRequest;
import com.example.authservice.DTO.UserDTO;
import com.example.authservice.exception.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

import java.rmi.ServerException;

@Service
public class UserVerificationService {

    private final WebClient webClient;

    public UserVerificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/user-service")
                .build();
    }

    public Mono<UserDTO> verifyUser(String email, String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        return webClient.post()
                .uri("/verify-credentials")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ClientException("Invalid credentials: " + errorBody))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ServerException("Server error: " + errorBody))))
                .bodyToMono(UserDTO.class)
                .onErrorResume(e -> {
                    if (e instanceof ClientException || e instanceof ServerException) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Unexpected error occurred", e));
                });
    }


}
