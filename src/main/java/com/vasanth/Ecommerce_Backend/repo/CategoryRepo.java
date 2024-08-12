package com.vasanth.Ecommerce_Backend.repo;

import com.vasanth.Ecommerce_Backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {

    Category findByCategoryName(String categoryName);
}