package com.example.userservice.Service;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.reservationservice.Model.Reservation;
import com.example.userservice.Model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void saveUser(User user);
    Livre getLivreById(int id, String token);
    List<Livre> getAllLivres(String token);
    List<Livre> getLivresByName(String titre, String token);
    List<Livre> getLivresByAuteur(String auteur, String token);
    List<Livre> getLivresByGenre(String genre, String token);
    List<Livre> getLivresByLangue(String langue, String token);
    List<Livre> getLivresByCategory(Category category, String token);
    void addReservation(Reservation reservation , String token);
    void deleteReservation(int id, String token);
    void updateReservationById(int id, Reservation reservation , String token);
    long count();

    long getTotalUserCount();
    Map<String, Long> getUserBreakdownByRole();

}
