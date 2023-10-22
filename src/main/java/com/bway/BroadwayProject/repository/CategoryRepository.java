package com.bway.BroadwayProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.BroadwayProject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
