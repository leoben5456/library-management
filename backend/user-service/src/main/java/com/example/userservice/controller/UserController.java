package com.example.userservice.controller;


import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import com.example.userservice.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/req/{id}")
    public ResponseEntity<Livre> test(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Livre livre = userService.getLivreById(id, token);
        return ResponseEntity.ok(livre);
    }
    @GetMapping("/reqlivres")
    public ResponseEntity<List<Livre>> getAllLivres(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getAllLivres(token);
        return ResponseEntity.ok(livres);
    }
    @GetMapping("/livre/titre/{titre}")
    public ResponseEntity<List<Livre>> getLivresByName(@RequestParam String titre, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getLivresByName(titre, token);
        return ResponseEntity.ok(livres);
    }
    @GetMapping("/livre/auteur/{auteur}")
    public ResponseEntity<List<Livre>> getLivresByAuteur(@RequestParam String auteur, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getLivresByAuteur(auteur, token);
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/livres/genre/{genre}")
    public ResponseEntity<List<Livre>> getLivresByGenre(@RequestParam String genre, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getLivresByGenre(genre, token);
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/livres/langue/{langue}")
    public ResponseEntity<List<Livre>> getLivresByLangue(@RequestParam String langue, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getLivresByLangue(langue, token);
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/livres/category/{category}")
    public ResponseEntity<List<Livre>> getLivresByCategory(@RequestParam Category category, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Livre> livres = userService.getLivresByCategory(category, token);
        return ResponseEntity.ok(livres);
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
