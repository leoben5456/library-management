package com.example.reservationservice.Controller;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Service.ReservationService;
import com.example.reservationservice.ServiceIMPL.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

@RestController

public class ReservationController {
    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;

    }



    @GetMapping("/welcome")
    public  ResponseEntity<String> test(){


        return ResponseEntity.ok("Hello from reservation service");
    }

    @PostMapping("/reservation/creation/{livreId}")
    public ResponseEntity<String> createReservation(@PathVariable int livreId, @Valid @RequestBody Reservation reservation, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (!reservationService.checkLivreAvailability(livreId, token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The book is not available");
            }

            // Get the book details
            Livre livre = reservationService.getLivreById(livreId, token);
            if (livre == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }

            // Decrement the quantity
            int newQuantite = livre.getQuantite() - 1;
            livre.setQuantite(newQuantite);

            // Update the status if quantity is 0
            if (newQuantite == 0) {
                livre.setStatus(Status.BORROWED);
            }

            // Save the updated book details
            reservationService.updateLivreStatus(livreId, livre.getStatus(), token);

            // Save the reservation
            Claims claims = JwtUtil.decodeJwt(token);
            reservation.setDateReservation(LocalDate.now());
            reservation.setLivreId(livreId);
            reservation.setEmailuser(claims.get("sub").toString());
            reservationService.saveReservation(reservation, token);

            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creation: " + e.getMessage());
        }
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<Livre> test(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Livre livre = reservationService.getLivreById(id, token);
        return ResponseEntity.ok(livre);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> getReservation(@PathVariable int id) {
        try {
            Reservation livre = reservationService.getReservation(id);

            if (livre == null) {
                // Return a structured error response
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Reservation not found", "The reservation with ID " + id + " does not exist."));
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

    @GetMapping("/reservation/check-livre-availability/{id}")
    public ResponseEntity<Boolean> checkLivreAvailability(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        boolean isAvailable = reservationService.checkLivreAvailability(id, token);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
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


    @GetMapping("/reservations")
    public ResponseEntity<Object> getAllReservations(Pageable pageable) {
        try {
            Page<Reservation> reservations = reservationService.getAllReservations(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(reservations);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching reservations.");
        }
    }


    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation deleted successfully!");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during deletion: " + e.getMessage());
        }
    }
    @PutMapping("/reservation/update/{id}")
    public ResponseEntity<String> updateReservationById(@PathVariable int id, @Valid @RequestBody Reservation reservation) {
        try {
            reservationService.updateReservationById(id, reservation);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation updated successfully!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during update: " + e.getMessage());
        }
    }


    @GetMapping("/reservation/user")
    public ResponseEntity<Object> getReservationsByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Pageable pageable) {
        try {
            Claims claims = JwtUtil.decodeJwt(token);
            String email = claims.get("sub").toString();
            Page<Reservation> reservations = reservationService.getReservationsByEmail(email, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(reservations);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching reservations.");
        }
    }



}

