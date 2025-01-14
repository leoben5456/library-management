package com.example.reservationservice.Repository;

import com.example.reservationservice.Model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findById(int id);
    void deleteById(int id);
    Page<Reservation> findAll(Pageable pageable);
    List<Reservation> findAllByIsReturnedFalseAndDateExpirationBefore(LocalDate date);

}
