package com.example.userservice.ServiceImpl;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.reservationservice.Model.Reservation;
import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, WebClient.Builder webClientBuilder, KafkaTemplate<String, Long> kafkaTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void saveUser(User user) {

        System.out.println("Role before saving: " + user.getRole());
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setTelephone(user.getTelephone());
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        long totalUsers = userRepository.count();
        kafkaTemplate.send("user-count-topic", totalUsers);
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
    public List<Livre> getAllLivres(String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livres")
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token)) // Forward the token
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block(); // Blocking for simplicity, consider reactive approach
    }
    @Override
    public List<Livre> getLivresByName(String titre, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livre/titre?titre=" + titre)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block();
    }
    @Override
    public List<Livre> getLivresByAuteur(String auteur, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livre/auteur?auteur=" + auteur)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block();
    }

    @Override
    public List<Livre> getLivresByGenre(String genre, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livres/genre?genre=" + genre)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block();
    }

    @Override
    public List<Livre> getLivresByLangue(String langue, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livres/langue?langue=" + langue)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block();
    }

    @Override
    public List<Livre> getLivresByCategory(Category category, String token) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/livre-service/livres/category?category=" + category)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToFlux(Livre.class)
                .collectList()
                .block();
    }

    @Override
    public void addReservation(Reservation reservation, String token) {
        WebClient webClient = webClientBuilder.build();

        webClient.post()
                .uri("http://localhost:8080/reservation-service/reservation/creation")
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteReservation(int id, String token) {
        WebClient webClient = webClientBuilder.build();

        webClient.delete()
                .uri("http://localhost:8080/reservation-service/deleteReservation/{id}", id)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void updateReservationById(int id, Reservation reservation, String token) {
        WebClient webClient = webClientBuilder.build();

        webClient.put()
                .uri("http://localhost:8080/reservation-service/reservation/update/{id}" , id)
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    @Override
    public long count(){
        return userRepository.count();
    }



}
