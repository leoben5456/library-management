package com.example.authservice.exception;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }
}