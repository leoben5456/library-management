package com.example.livreservice.Repository;

import com.example.livreservice.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);
    Optional<Category> findByName(String name);
}
