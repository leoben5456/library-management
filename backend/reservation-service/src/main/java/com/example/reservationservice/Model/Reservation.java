package com.example.reservationservice.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateReservation;
    private LocalDate dateExpiration;
    private String titreLiver;
    private String emailuser;
    private int livreId;
    private String dayOfWeek;
    private boolean isReturned=false;
    private LocalDate dateReturned;
    private double penalty=0.0;


    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
        this.dayOfWeek = (dateReservation != null) ? dateReservation.getDayOfWeek().name() : null;
    }

}

