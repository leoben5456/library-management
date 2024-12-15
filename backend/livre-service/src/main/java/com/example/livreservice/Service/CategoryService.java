package com.example.livreservice.Service;

import com.example.livreservice.Model.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    void deleteCategory(int id);
    void updateCategory(Category category);
    Category getCategory(int id);
    List<Category> getAllCategories();
    Category getCategoryByName(String name);
}
