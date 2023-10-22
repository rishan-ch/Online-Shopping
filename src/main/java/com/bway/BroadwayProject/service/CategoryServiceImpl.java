package com.bway.BroadwayProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.BroadwayProject.model.Category;
import com.bway.BroadwayProject.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository catRepo;

    @Override
    public void addCategory(Category category) {
        catRepo.save(category);
    }

    @Override
    public java.util.List<Category> getAllCategory() {
        return catRepo.findAll();
    }

    @Override
    public void updateCategory(Category category) {
        catRepo.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        catRepo.deleteById(id);
    }

    @Override
    public Optional<Category> getCategory(int id) {
        return catRepo.findById(id);
    }
    
}
