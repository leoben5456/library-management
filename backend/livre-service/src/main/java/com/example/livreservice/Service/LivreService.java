package com.example.livreservice.Service;

import com.example.livreservice.DTO.LivreDTO;
import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LivreService {
    void saveLivre(Livre livre);
    void deleteLivre(int id);
    void updateLivre(Livre livre);
    Livre getLivre(int id);
    List<Livre> getAllLivres();


}
