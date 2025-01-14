package com.example.reservationservice.ServiceIMPL;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import com.example.livreservice.Service.LivreService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void saveReservation(Reservation reservation, String token) {
        if (!checkLivreAvailability(reservation.getLivreId(), token)) {
            throw new IllegalArgumentException("The book is not available");
        }
        reservation.setDateReservation(java.time.LocalDate.now());
        reservationRepository.save(reservation);
        // Get the book details
        Livre livre = getLivreById(reservation.getLivreId(), token);
        // Call the updateLivre endpoint to update the book details
        WebClient webClient = webClientBuilder.build();
        webClient.put()
                .uri("http://localhost:8080/livre-service/livre/update/" + livre.getId())
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token)) // Forward the token
                .bodyValue(livre)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking for simplicity, consider reactive approach


    }



    public Reservation getReservation(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
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

    @Override
    public boolean checkLivreAvailability(int id, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livre/is-available/" + id)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token)) // Forward the token
                .retrieve()
                .bodyToMono(Boolean.class)
                .block(); // Blocking for simplicity, consider reactive approach
    }

    @Override
    public void updateLivreStatus(int livreId, Status status, String token) {

        WebClient webClient = webClientBuilder.build();

        webClient.put()
                .uri("http://localhost:8080/livre-service/livre/update-status/" + livreId)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token)) // Forward the token
                .bodyValue(status)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Blocking for simplicity, consider reactive approach
    }


}
