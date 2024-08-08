package com.vasanth.Ecommerce_Backend.controller;

import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<List<Product>>(productService.getAllProducts(),HttpStatus.OK);
    }
}
