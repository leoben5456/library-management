package com.example.reservationservice.Service;


import com.example.reservationservice.Model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PenaltyService {

    private static final double PENALTY_PER_DAY = 10.0;
    public double calculatePenalty(Reservation reservation) {
        if (reservation.getDateExpiration().isBefore(LocalDate.now()) && !reservation.isReturned()) {
            long overdueDays = ChronoUnit.DAYS.between(reservation.getDateExpiration(), LocalDate.now());
            return overdueDays * PENALTY_PER_DAY;
        }
        return 0.0;
    }
}
