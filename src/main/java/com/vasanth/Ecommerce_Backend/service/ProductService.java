package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.exceptions.APIException;
import com.vasanth.Ecommerce_Backend.exceptions.ResourceNotFoundException;
import com.vasanth.Ecommerce_Backend.model.Category;
import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.payload.CommonMapper;
import com.vasanth.Ecommerce_Backend.payload.ProductDTO;
import com.vasanth.Ecommerce_Backend.payload.ProductResponse;
import com.vasanth.Ecommerce_Backend.repo.CategoryRepo;
import com.vasanth.Ecommerce_Backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private CategoryRepo categoryRepo;

    public ProductDTO addProduct(UUID categoryId, Product product) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        boolean isNewProduct = true;

        List<Product> products = category.getProducts();

        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductName().equals(product.getProductName())
                && products.get(i).getDescription().equals(product.getDescription())){
                isNewProduct = false;
                break;
            }
        }

        if(isNewProduct){
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);

            Product saveProduct = repo.save(product);

            return CommonMapper.INSTANCE.toProductDTO(saveProduct);
        }

        else {
            throw new APIException("Product already exists");
        }

    }

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
        List<ProductDTO> productDTO = products.stream().map(CommonMapper.INSTANCE::toProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pagedProducts.getNumber());
        productResponse.setPageSize(pagedProducts.getSize());
        productResponse.setTotalElements(pagedProducts.getTotalElements());
        productResponse.setTotalPages(pagedProducts.getTotalPages());
        productResponse.setLastPage(pagedProducts.isLast());

        return productResponse;
    }


}
