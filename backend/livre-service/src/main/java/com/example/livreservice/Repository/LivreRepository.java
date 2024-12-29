package com.example.livreservice.Repository;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Optional<Livre> findByTitreAndAuteur(String titre, String auteur);
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Livre l WHERE l.titre = :title AND l.isReserved = FALSE")
    boolean existsAndIsAvailableByTitle(String title);


}
