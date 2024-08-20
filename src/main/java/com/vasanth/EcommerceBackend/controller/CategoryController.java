package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.config.AppConstants;
import com.vasanth.EcommerceBackend.model.Category;
import com.vasanth.EcommerceBackend.payload.CategoryDTO;
import com.vasanth.EcommerceBackend.payload.CategoryResponse;
import com.vasanth.EcommerceBackend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category){
        CategoryDTO categoryDTO = categoryService.createCategory(category);
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.CREATED);

    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_CATEGORY_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber,pageSize,sortBy,sortDir);

        return new  ResponseEntity<CategoryResponse>(categoryResponse,HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody Category category, @PathVariable("categoryId") UUID categoryId){

        CategoryDTO categoryDTO = categoryService.updateCategory(category,categoryId);

        return new ResponseEntity<CategoryDTO>(categoryDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") UUID categoryId){

        String status = categoryService.deleteCategory(categoryId);

        return new ResponseEntity<String>(status,HttpStatus.OK);

    }

}
