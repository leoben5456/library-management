package com.example.livreservice.ServiceImpl;

import com.example.livreservice.Model.Category;
import com.example.livreservice.Model.Livre;
import com.example.livreservice.Repository.CategoryRepository;
import com.example.livreservice.Repository.LivreRepository;
import com.example.livreservice.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override

    public void saveCategory(Category category) {
        category.setName(category.getName());
        category.setNbLivres(category.getNbLivres());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public Category getCategory(int id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        } else {
            return null;
        }

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            return categoryRepository.findByName(name).get();
        } else {
            return null;
        }

    }

}
