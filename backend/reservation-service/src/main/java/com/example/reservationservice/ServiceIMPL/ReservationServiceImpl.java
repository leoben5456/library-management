package com.example.reservationservice.ServiceIMPL;

import com.example.livreservice.Model.Livre;
import com.example.reservationservice.Model.Reservation;
import com.example.reservationservice.Repository.ReservationRepository;
import com.example.reservationservice.Service.ReservationService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final WebClient.Builder webClientBuilder ;

    public ReservationServiceImpl(ReservationRepository reservationRepository, WebClient.Builder webClientBuilder) {
        this.reservationRepository = reservationRepository;
        this.webClientBuilder = webClientBuilder;
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


    @Override
    public Livre getLivreById(int id, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livre/" + id)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token)) // Forward the token
                .retrieve()
                .bodyToMono(Livre.class)
                .block(); // Blocking for simplicity, consider reactive approach
    }


}
