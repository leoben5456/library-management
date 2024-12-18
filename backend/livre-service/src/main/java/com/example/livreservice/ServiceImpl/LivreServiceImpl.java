package com.example.livreservice.ServiceImpl;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.livreservice.Repository.CategoryRepository;
import com.example.livreservice.Repository.LivreRepository;
import com.example.livreservice.Service.LivreService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreServiceImpl implements LivreService {

    private final LivreRepository livreRepository;
    private final CategoryRepository categoryRepository;


    public LivreServiceImpl(LivreRepository livreRepository, CategoryRepository categoryRepository) {
        this.livreRepository = livreRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void saveLivre(Livre livre) {
        // Handle category logic
        String categoryName = livre.getCategory().getName();
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);

        Category category;
        if (existingCategory.isPresent()) {
            category = existingCategory.get();
            category.setNbLivres(category.getNbLivres() + 1);
        } else {
            category = new Category();
            category.setName(categoryName);
            category.setNbLivres(1);
        }

        categoryRepository.save(category);

        // Associate the book with the category
        livre.setCategory(category);
        livre.setCategoryName(category.getName());
        // Assign other attributes (Optional but illustrative)
        livre.setTitre(livre.getTitre());
        livre.setAuteur(livre.getAuteur());
        livre.setGenre(livre.getGenre());
        livre.setImage(livre.getImage());
        livre.setDatePublication(livre.getDatePublication());
        livre.setLangue(livre.getLangue());
        livre.setNbPages(livre.getNbPages());
        livre.setPrix(livre.getPrix());
        livre.setStatus(livre.getStatus());

        // Save the book
        livreRepository.save(livre);
    }
    @Override
    @Transactional
    public void deleteLivre(int id) {
        livreRepository.deleteById(id);
    }
    @Override
    @Transactional
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

    @Override
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    @Override
    public Livre getLivreByTitre(String titre) {
        return livreRepository.findByTitre(titre).orElse(null);
    }

    @Override
    public List<Livre> getLivresByAuteur(String auteur) {
        return livreRepository.findByAuteur(auteur);
    }

    @Override
    public List<Livre> getLivresByGenre(String genre) {
        return livreRepository.findByGenre(genre);
    }

    @Override
    public List<Livre> getLivresByLangue(String langue) {
        return livreRepository.findByLangue(langue);
    }

    @Override
    public List<Livre> getLivresByCategory(Category category) {
        return livreRepository.findByCategory(category);
    }


}
