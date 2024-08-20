package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {

    Category findByCategoryName(String categoryName);


}
