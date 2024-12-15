package com.example.authservice.controller;


import com.example.authservice.DTO.LoginRequest;
import com.example.authservice.DTO.LoginResponse;
import com.example.authservice.DTO.UserDTO;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class authentication {

    private  final AuthService authService;

    public authentication(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

       LoginResponse Response= authService.authenticate(loginRequest);
        if (Response != null) {
            return ResponseEntity.ok(Response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }



}
