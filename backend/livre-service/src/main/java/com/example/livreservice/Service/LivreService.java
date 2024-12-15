package com.example.livreservice.Service;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import org.hibernate.validator.constraints.CodePointLength;

import java.util.List;


public interface LivreService {
    void saveLivre(Livre livre);
    void deleteLivre(int id);
    void updateLivre(Livre livre);
    Livre getLivre(int id);
    List<Livre> getAllLivres();

}
