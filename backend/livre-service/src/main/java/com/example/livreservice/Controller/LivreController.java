package com.example.livreservice.Controller;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Service.LivreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LivreController {
    private final LivreService livreService;


    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Livre Service";
    }

    @PostMapping("/livre/inscription")
    public ResponseEntity<String> inscription(@Valid @RequestBody Livre livre) {
        try {
            System.out.println("livre before saving: " + livre);
            livreService.saveLivre(livre);
            return ResponseEntity.status(HttpStatus.CREATED).body("livre registered successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration: " + e.getMessage());
        }
    }


    @DeleteMapping("/livre/delete/{id}")
    public ResponseEntity<String> deleteLivre(@PathVariable int id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.status(HttpStatus.OK).body("livre deleted successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during deletion: " + e.getMessage());
        }
    }



    @PutMapping("/livre/update")
    public ResponseEntity<String> updateLivre(@Valid @RequestBody Livre livre) {
        try {
            livreService.updateLivre(livre);
            return ResponseEntity.status(HttpStatus.OK).body("livre updated successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during update: " + e.getMessage());
        }
    }
    @GetMapping("/livre/{id}")
    public ResponseEntity<?> getLivre(@PathVariable int id) {
        try {
            Livre livre = livreService.getLivre(id);

            if (livre == null) {
                // Return a structured error response
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Livre not found", "The book with ID " + id + " does not exist."));
            }

            return ResponseEntity.status(HttpStatus.OK).body(livre);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, "Invalid input", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred. Please try again later."));
        }
    }

    // Inner class for API error response
    public static class ApiError {
        private int status;
        private String error;
        private String message;

        public ApiError(HttpStatus status, String error, String message) {
            this.status = status.value();
            this.error = error;
            this.message = message;
        }

        // Getters and Setters
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    @GetMapping("/livres")
    public ResponseEntity<Object> getAllLivres() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(livreService.getAllLivres());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        }
    }


