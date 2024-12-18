package com.example.livreservice.Repository;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    Optional<Livre> findById(int id);
    Optional<Livre> findByTitre(String titre);
    List<Livre> findByAuteur(String auteur);
    List<Livre> findByGenre(String genre);
    List<Livre> findByLangue(String langue);
    List<Livre> findByCategory(Category category);
}
