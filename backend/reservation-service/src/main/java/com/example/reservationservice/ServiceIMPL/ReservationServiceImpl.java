package com.example.reservationservice.ServiceIMPL;

import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import com.example.reservationservice.Service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        reservationRepository.save(reservation);
    }



}
