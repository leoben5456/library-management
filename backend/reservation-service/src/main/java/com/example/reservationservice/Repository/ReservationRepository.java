package com.example.reservationservice.Repository;

import com.example.reservationservice.Model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT r FROM Reservation r WHERE r.emailuser = :email")
    Page<Reservation> getReservationsByEmail(String email, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.isReturned = false")
    int countTotalBorrowed();

    @Query("SELECT r.dayOfWeek, COUNT(r) FROM Reservation r WHERE r.isReturned = false GROUP BY r.dayOfWeek")
    List<Object[]> countBorrowedByDayOfWeek();

    // Get count of penalties for the current month
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.penalty > 0 AND MONTH(r.dateReturned) = MONTH(CURRENT_DATE) AND YEAR(r.dateReturned) = YEAR(CURRENT_DATE)")
    int countPenaltiesThisMonth();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.dateExpiration < CURRENT_DATE AND r.isReturned = false")
    int countOverdueBooks();


}
