package com.example.userservice.controller;


import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import com.example.userservice.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private  final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to User Service";
    }



    @PostMapping("/user/inscription")
    public ResponseEntity<String> inscription(@Valid @RequestBody User user) {
        try {
            System.out.println("User before saving: " + user);
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration: " + e.getMessage());
        }
    }


    @PostMapping("/verify-credentials")
    public ResponseEntity<?> verifyCredentials(@Valid @RequestBody User user) {
        try {
            var userOptional = userRepository.findByEmail(user.getEmail());
            if (userOptional.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found.");
            }

            User userFound = userOptional.get();
            UserDTO userDTO = new UserDTO(userFound.getEmail(), userFound.getRole());

            boolean passwordMatches = passwordEncoder.matches(user.getPassword(), userFound.getPassword());
            if (!passwordMatches) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials");
            }


            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal error occurred. Please try again later.");
        }
    }


}
