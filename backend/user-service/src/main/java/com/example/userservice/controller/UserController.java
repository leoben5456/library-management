package com.example.userservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to User Service";
    }
}
