package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }
}
