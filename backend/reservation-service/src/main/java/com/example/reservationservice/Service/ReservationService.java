package com.example.reservationservice.Service;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import com.example.reservationservice.DTO.BorrowStatisticsDTO;
import com.example.reservationservice.Model.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public interface ReservationService {
    Reservation getReservation(int id);
    Page<Reservation> getAllReservations(Pageable pageable);
    void deleteReservation(int id);
    void updateReservationById(int id, Reservation reservation);

    @Transactional
    void updateReservationReturnedById(int id, boolean returned);

    Livre getLivreById(int id, String token);
   // boolean isLivreAvailable(int livreId, String token);
    void saveReservation(Reservation reservation, String token);
    boolean checkLivreAvailability(int id, String token);
    void updateLivreStatus(int livreId, Status status, String token);

    Page<Reservation> getReservationsByEmail(String email, Pageable pageable);

    BorrowStatisticsDTO getBorrowStatistics();

    int getNumberOfPenaltiesThisMonth();

    int getOverdueBookCount();
}
