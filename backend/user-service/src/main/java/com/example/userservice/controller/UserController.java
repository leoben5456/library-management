package com.example.userservice.controller;


import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.reservationservice.Model.Reservation;
import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import com.example.userservice.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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
    @PostMapping("/reservation/creation")
    public ResponseEntity<String> addReservation(@RequestBody Reservation reservation, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            userService.addReservation(reservation, token);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during reservation creation: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            userService.deleteReservation(id, token);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reservation deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during reservation deletion: " + e.getMessage());
        }
    }

    @PutMapping("/reservation/update/{id}")
    public ResponseEntity<String> updateReservationById(@PathVariable int id, @RequestBody Reservation reservation, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            userService.updateReservationById(id, reservation, token);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during reservation update: " + e.getMessage());
        }
    }



    // The folder where profile images will be stored
    private static final String UPLOAD_DIR = "/home/naoufal-ben/IdeaProjects/library-management/backend/uploads/user-profile/";

    @PostMapping(value = "/user/inscription", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> inscription(
            @RequestPart("user") String userJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            // Parse the user JSON
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);

            // If a file is included, handle the upload
            if (file != null && !file.isEmpty()) {
                // Generate a unique filename (optional)
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

                // Build the target location
                Path targetLocation = Paths.get(UPLOAD_DIR + uniqueFilename);
                // Copy the file to the target location
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // Set the path or URL in the user entity (storing only the filename or the complete URL)
                user.setProfilePicturePath(uniqueFilename);
            }

            // Save the user
            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not parse user data: " + e.getMessage());
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
            UserDTO userDTO = new UserDTO(userFound.getEmail(), userFound.getRole(),user.getProfilePicturePath());
            System.out.println("User details:"+userDTO);
            boolean passwordMatches = passwordEncoder.matches(user.getPassword(), userFound.getPassword());
            if (!passwordMatches) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials");
            }

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            logger.error("An error occurred while verifying credentials", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal error occurred: " + e.getMessage());
        }
    }





}
