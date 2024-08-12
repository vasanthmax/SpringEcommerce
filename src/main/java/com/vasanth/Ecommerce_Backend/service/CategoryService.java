package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.exceptions.APIException;
import com.vasanth.Ecommerce_Backend.exceptions.ResourceNotFoundException;
import com.vasanth.Ecommerce_Backend.model.Category;
import com.vasanth.Ecommerce_Backend.payload.CategoryDTO;
import com.vasanth.Ecommerce_Backend.payload.CategoryResponse;
import com.vasanth.Ecommerce_Backend.payload.CommonMapper;
import com.vasanth.Ecommerce_Backend.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());

        if (savedCategory != null){
            throw new APIException("Category "+ category.getCategoryName()+" already exists");
        }

        savedCategory = categoryRepo.save(category);

        return CommonMapper.INSTANCE.toCategoryDTO(savedCategory);
    }

    public CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortDir){

        //Sorting
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():
                    Sort.by(sortBy).descending();

        //Pagination
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> pagedcategories = categoryRepo.findAll(pageDetails);

        List<Category> categories = pagedcategories.getContent();

        if(categories.isEmpty()){
            throw new APIException("Category is Empty");
        }

        //Converting Category to Category DTO
        List<CategoryDTO> categoryDTO = categories.stream().map(CommonMapper.INSTANCE::toCategoryDTO).toList();

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTO);
        categoryResponse.setPageNumber(pagedcategories.getNumber());
        categoryResponse.setPageSize(pagedcategories.getSize());
        categoryResponse.setTotalElements(pagedcategories.getTotalElements());
        categoryResponse.setTotalPages(pagedcategories.getTotalPages());
        categoryResponse.setLastPage(pagedcategories.isLast());

        return categoryResponse;
    }

    public CategoryDTO updateCategory(Category category, UUID categoryId) {

        Category savedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(categoryId);

        savedCategory = categoryRepo.save(category);

        return CommonMapper.INSTANCE.toCategoryDTO(savedCategory);

    }

    public String deleteCategory(UUID categoryId) {

        Category savedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepo.delete(savedCategory);

        return "Category Deleted with the Id:" + categoryId;

    }
}
