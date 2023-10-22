package com.bway.BroadwayProject.service;


import java.util.Optional;

import com.bway.BroadwayProject.model.Category;

public interface CategoryService {
    void addCategory(Category category);
    java.util.List<Category> getAllCategory();
    Optional<Category> getCategory(int id);
    void updateCategory(Category category);
    void deleteCategory(int id);
}
