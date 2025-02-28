package com.example.reservationservice.Service;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PenaltyUpdateScheduler {

    private final ReservationRepository reservationRepository;
    private final PenaltyService penaltyService;

    private  final  KafkaTemplate<String, String> kafkaTemplate;

    public PenaltyUpdateScheduler(ReservationRepository reservationRepository, PenaltyService penaltyService, KafkaTemplate<String, String> kafkaTemplate) {
        this.reservationRepository = reservationRepository;
        this.penaltyService = penaltyService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "0 0 0 * * ?")// Runs daily at midnight
    public void checkAndUpdatePenalties() {
        List<Reservation> overdueReservations = reservationRepository.findAllByIsReturnedFalseAndDateExpirationBefore(LocalDate.now());
        for (Reservation reservation : overdueReservations) {
            double penalty = penaltyService.calculatePenalty(reservation);
            reservation.setPenalty(penalty);
            reservationRepository.save(reservation);

            //Create notification message structure
            String notificationMessage = String.format(
                    "{\"email\":\"%s\", \"message\":\"Dear our user, your book loan is overdue. The penalty cost is: $%.2f. Please return the book as soon as possible.\"}",
                    reservation.getEmailuser(), penalty);

            kafkaTemplate.send("notification-topic", reservation.getEmailuser(), notificationMessage);
        }
    }
}