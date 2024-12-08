package com.example.reservationservice.Controller;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            System.out.println(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully!");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creation: " + e.getMessage());
        }
    }
}
