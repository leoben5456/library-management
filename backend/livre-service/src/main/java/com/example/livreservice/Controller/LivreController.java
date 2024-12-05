package com.example.livreservice.Controller;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Service.LivreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Livre> getLivre(@PathVariable int id) {
        try {
            Livre livre = livreService.getLivre(id);
            return ResponseEntity.status(HttpStatus.OK).body(livre);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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


