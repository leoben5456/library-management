package com.example.authservice.exception;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}