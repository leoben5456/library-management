package com.example.userservice.ServiceImpl;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, WebClient.Builder webClientBuilder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public void saveUser(User user) {


        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setTelephone(user.getTelephone());
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);


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



}
