package com.example.reservationservice.Service;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PenaltyUpdateScheduler {

    private final ReservationRepository reservationRepository;
    private final PenaltyService penaltyService;
    private final RabbitTemplate rabbitTemplate;

    private  final  KafkaTemplate<String, String> kafkaTemplate;

    public PenaltyUpdateScheduler(ReservationRepository reservationRepository, PenaltyService penaltyService, RabbitTemplate rabbitTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.reservationRepository = reservationRepository;
        this.penaltyService = penaltyService;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndUpdatePenalties() throws JsonProcessingException {
        List<Reservation> overdueReservations = reservationRepository.findAllByIsReturnedFalseAndDateExpirationBefore(LocalDate.now());
        for (Reservation reservation : overdueReservations) {
            double penalty = penaltyService.calculatePenalty(reservation);
            reservation.setPenalty(penalty);
            reservationRepository.save(reservation);

            //Create notification message structure
            String notificationMessage = String.format(
                    "\"Dear our user, your book loan is overdue. The penalty cost is: $%.2f. Please return the book as soon as possible.\"", penalty);

            Map<String, String> notification = new HashMap<>();

            notification.put("email", reservation.getEmailuser());
            notification.put("message",notificationMessage);

            rabbitTemplate.convertAndSend("notificationQueue",notification );
        }
    }
}