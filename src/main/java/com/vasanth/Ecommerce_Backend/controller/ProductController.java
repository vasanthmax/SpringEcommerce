package com.vasanth.Ecommerce_Backend.controller;

import com.vasanth.Ecommerce_Backend.config.AppConstants;
import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.payload.ProductResponse;
import com.vasanth.Ecommerce_Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortDir
    ){

        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }
}
