package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.config.AppConstants;
import com.vasanth.EcommerceBackend.model.Product;
import com.vasanth.EcommerceBackend.payload.ProductDTO;
import com.vasanth.EcommerceBackend.payload.ProductResponse;
import com.vasanth.EcommerceBackend.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO>  addProduct(@Valid @RequestBody Product product, @PathVariable("categoryId") UUID categoryId){
        ProductDTO productDTO = productService.addProduct(categoryId,product);

        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.CREATED);
    }

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

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> searchByKeyword(
            @PathVariable("keyword") String keyword,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir

    ){
        ProductResponse productResponse = productService.searchByKeyword(keyword,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable("categoryId") UUID categoryId,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
            ){

        ProductResponse productResponse = productService.getProductsByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);

    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody Product product,@PathVariable("productId") UUID productId){
        ProductDTO productDTO = productService.updateProduct(productId,product);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable("productId") UUID productId, @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO productDTO = productService.updateProductImage(productId,image);

        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);

    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") UUID productId){
        String response = productService.deleteProduct(productId);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }


}
