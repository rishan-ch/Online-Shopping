package com.bway.BroadwayProject.service;


import java.util.List;
import java.util.Optional;

import com.bway.BroadwayProject.model.Product;

public interface ProductService {

    void addProduct(Product product);
    void delProduct(Long id);
    void updateProduct(Product product);
    List<Product> getAllProduct();
    Optional<Product> getProduct(Long id);
    List<Product> getAllProductByCategoryId(int id);
    
}
