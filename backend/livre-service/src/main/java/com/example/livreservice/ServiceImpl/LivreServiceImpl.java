package com.example.livreservice.ServiceImpl;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import com.example.livreservice.Repository.CategoryRepository;
import com.example.livreservice.Repository.LivreRepository;
import com.example.livreservice.Service.LivreService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        String categoryName = livre .getCategory().getName();
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

        // Check if the book already exists
        Optional<Livre> existingLivre = livreRepository.findByTitreAndAuteur(livre.getTitre(), livre.getAuteur());
        if (existingLivre.isPresent()) {
            // Increment the quantity of the existing book
            Livre existing = existingLivre.get();
            existing.setQuantite(existing.getQuantite() + 1);
            livreRepository.save(existing);
        } else {
            // Associate the book with the category
            livre.setCategory(category);
            livre.setCategoryName(category.getName());
            livre.setQuantite(1); // Set initial quantity to 1

            // Save the new book
            livreRepository.save(livre);
        }
    }
    @Override
    @Transactional
    public void deleteLivre(int id) {
        livreRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void updateLivre(Livre livre) {
        Optional<Livre> existingLivreOpt = livreRepository.findById(livre.getId());
        if (existingLivreOpt.isPresent()) {
            Livre existingLivre = existingLivreOpt.get();

            if (existingLivre.getQuantite() > 0) {
                existingLivre.setQuantite(existingLivre.getQuantite() - 1);

                if (existingLivre.getQuantite() == 0) {
                    existingLivre.setStatus(Status.BORROWED);
                }
            } else {
                throw new IllegalStateException("Quantity already zero");
            }

            livreRepository.save(existingLivre);
        } else {
            throw new IllegalArgumentException("Livre not found");
        }
    }
    public Livre getLivre(int id) {
        if (livreRepository.findById(id).isPresent()) {
            return livreRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public Page<Livre> getAllLivres(Pageable pageable){
        return livreRepository.findAll(pageable);

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

    @Override
    public boolean isLivreAvailable(int id) {
        Livre livre = livreRepository.findById(id).orElse(null);
        return livre != null && "available".equalsIgnoreCase(String.valueOf(livre.getStatus()));
    }

    @Override
    public void updateLivreStatus(int id, Status status) {
        Livre livre = livreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livre not found"));
        livre.setStatus(status);
        livreRepository.save(livre);
    }

    @Override
    public void incrementQuantite() {
        List<Livre> livres = livreRepository.findAll();
        for (Livre livre : livres) {
            livre.setQuantite(livre.getQuantite() + 1);
            livreRepository.save(livre);
        }
    }
    @Override
    public void decrementQuantite() {
        List<Livre> livres = livreRepository.findAll();
        for (Livre livre : livres) {
            livre.setQuantite(livre.getQuantite() - 1);
            livreRepository.save(livre);
        }
    }


}
