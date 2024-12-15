package com.example.livreservice.Repository;

import com.example.livreservice.Model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    Optional<Livre> findById(int id);
    Optional<Livre> findByTitre(String titre);
    Optional<Livre> findByAuteur(String auteur);
}
