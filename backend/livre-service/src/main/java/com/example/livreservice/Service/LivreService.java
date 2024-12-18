package com.example.livreservice.Service;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LivreService {
    void saveLivre(Livre livre);
    void deleteLivre(int id);
    void updateLivre(Livre livre);
    Livre getLivre(int id);
    List<Livre> getAllLivres();
    Livre getLivreByTitre(String titre);
    List<Livre> getLivresByAuteur(String auteur);
    List<Livre> getLivresByGenre(String genre);
    List<Livre> getLivresByLangue(String langue);
    List<Livre> getLivresByCategory(Category category);

}
