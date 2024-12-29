package com.example.livreservice.Service;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public interface LivreService {
    void saveLivre(Livre livre);
    void deleteLivre(int id);
    void updateLivre(Livre livre);
    Livre getLivre(int id);
    public Page<Livre> getAllLivres(Pageable pageable);
    Livre getLivreByTitre(String titre);
    List<Livre> getLivresByAuteur(String auteur);
    List<Livre> getLivresByGenre(String genre);
    List<Livre> getLivresByLangue(String langue);
    List<Livre> getLivresByCategory(Category category);
    boolean isLivreAvailable(int id);
    void updateLivreStatus(int id, Status status);
    void incrementQuantite();
    void decrementQuantite();
}
