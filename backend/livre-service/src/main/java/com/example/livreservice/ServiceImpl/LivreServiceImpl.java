package com.example.livreservice.ServiceImpl;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Repository.LivreRepository;
import com.example.livreservice.Service.LivreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreServiceImpl implements LivreService {
    private final LivreRepository livreRepository;

    public LivreServiceImpl(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @Override

    public void saveLivre(Livre livre) {
        livre.setTitre(livre.getTitre());
        livre.setAuteur(livre.getAuteur());
         livre.setGenre(livre.getGenre());
        livre.setImage(livre.getImage());
        livre.setDatePublication(livre.getDatePublication());
        livre.setLangue(livre.getLangue());
        livre.setNbPages(livre.getNbPages());
        livre.setPrix(livre.getPrix());
        livreRepository.save(livre);
    }
    public void deleteLivre(int id) {
        livreRepository.deleteById(id);
    }
    public void updateLivre(Livre livre) {
        livreRepository.save(livre);
    }
    public Livre getLivre(int id) {
        if (livreRepository.findById(id).isPresent()) {
            return livreRepository.findById(id).get();
        } else {
            return null;
        }
    }
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }


}
