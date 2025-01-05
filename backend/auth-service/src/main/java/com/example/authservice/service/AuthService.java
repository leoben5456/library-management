package com.example.authservice.service;


import com.example.authservice.DTO.LoginRequest;
import com.example.authservice.DTO.LoginResponse;
import com.example.authservice.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private  final JwtService jwtService;

    private  final UserVerificationService verificationService;

    public AuthService(JwtService jwtService, UserVerificationService verificationService) {
        this.jwtService = jwtService;
        this.verificationService = verificationService;
    }


    public LoginResponse authenticate(LoginRequest loginRequest){

        UserDTO userDTO = verificationService.verifyUser(loginRequest.getEmail(), loginRequest.getPassword())
                .block();

        if (userDTO != null) {
            String accessToken = jwtService.generateToken(userDTO);
            String refreshToken = jwtService.generateRefreshToken(userDTO);
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
            return loginResponse;
        } else {
            return null;
        }
    }

}
