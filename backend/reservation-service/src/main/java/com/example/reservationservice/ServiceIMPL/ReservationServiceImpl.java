package com.example.reservationservice.ServiceIMPL;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import com.example.reservationservice.Service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

    }

    @Override
    @Transactional
    public void saveReservation(Reservation reservation) {
        reservation.setDateReservation(reservation.getDateReservation());
        reservation.setDateExpiration(reservation.getDateExpiration());
        reservation.setTitreLiver(reservation.getTitreLiver());
        reservation.setEmailuser(reservation.getEmailuser());
        reservationRepository.save(reservation);
    }
    public Reservation getReservation(int id) {
        return reservationRepository.findById(id).orElse(null);
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void updateReservationById(int id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        existingReservation.setDateReservation(reservation.getDateReservation());
        existingReservation.setDateExpiration(reservation.getDateExpiration());
        existingReservation.setTitreLiver(reservation.getTitreLiver());
        existingReservation.setEmailuser(reservation.getEmailuser());
        reservationRepository.save(existingReservation);
    }




}
