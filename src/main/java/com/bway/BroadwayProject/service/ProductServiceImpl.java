package com.bway.BroadwayProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.BroadwayProject.model.Product;
import com.bway.BroadwayProject.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository prodRepo;

    @Override
    public void addProduct(Product product) {
        prodRepo.save(product);
    }

    @Override
    public void delProduct(Long id) {
        prodRepo.deleteById(id);
    }

    @Override
    public void updateProduct(Product product) {
        prodRepo.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return prodRepo.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return prodRepo.findById(id);
    }

    @Override
    public List<Product> getAllProductByCategoryId(int id) {
        return prodRepo.findAllByCategory_Id(id);
    }

    
    
}
