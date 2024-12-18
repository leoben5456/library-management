package com.example.reservationservice.Controller;

import com.example.livreservice.Model.Livre;
import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController

public class ReservationController {
    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;

    }
    @PostMapping("/reservation/creation")
    public ResponseEntity<String> createReservation(@Valid @RequestBody Reservation reservation) {
        try {
            reservationService.saveReservation(reservation);
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
    public ResponseEntity<Object> getAllReservations() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservationService.getAllReservations());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

}

