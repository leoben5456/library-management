package com.example.reservationservice.Service;

import com.example.reservationservice.Model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ReservationService {
    void saveReservation(Reservation reservation);
    Reservation getReservation(int id);
    List<Reservation> getAllReservations();
    void deleteReservation(int id);
    void updateReservationById(int id, Reservation reservation);




}
