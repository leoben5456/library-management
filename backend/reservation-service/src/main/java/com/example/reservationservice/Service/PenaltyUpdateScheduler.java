package com.example.reservationservice.Service;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PenaltyUpdateScheduler {

    private final ReservationRepository reservationRepository;
    private final PenaltyService penaltyService;

    public PenaltyUpdateScheduler(ReservationRepository reservationRepository, PenaltyService penaltyService) {
        this.reservationRepository = reservationRepository;
        this.penaltyService = penaltyService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void checkAndUpdatePenalties() {
        List<Reservation> overdueReservations = reservationRepository.findAllByIsReturnedFalseAndDateExpirationBefore(LocalDate.now());
        for (Reservation reservation : overdueReservations) {
            double penalty = penaltyService.calculatePenalty(reservation);
            reservation.setPenalty(penalty);
            reservationRepository.save(reservation);
        }
    }
}