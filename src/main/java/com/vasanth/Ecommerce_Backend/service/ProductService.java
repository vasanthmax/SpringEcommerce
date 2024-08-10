package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.payload.ProductDTO;
import com.vasanth.Ecommerce_Backend.payload.ProductMapper;
import com.vasanth.Ecommerce_Backend.payload.ProductResponse;
import com.vasanth.Ecommerce_Backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;



    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir){

        //Sorting
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
                    Sort.by(sortBy).descending();

        //Pagination
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pagedProducts = repo.findAll(pageDetails);

        //Get Only Products from Paged Products
        List<Product> products = pagedProducts.getContent();

        //Converting to Product DTO
        List<ProductDTO> productDTO = products.stream().map(ProductMapper.INSTANCE::toProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pagedProducts.getNumber());
        productResponse.setPageSize(pagedProducts.getSize());
        productResponse.setTotalElements(pagedProducts.getTotalElements());
        productResponse.setTotalPages(pagedProducts.getTotalPages());
        productResponse.setLastPage(productResponse.isLastPage());

        return productResponse;
    }
}
