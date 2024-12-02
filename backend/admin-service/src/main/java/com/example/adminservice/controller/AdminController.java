package com.example.adminservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Admin Service";
    }
}
