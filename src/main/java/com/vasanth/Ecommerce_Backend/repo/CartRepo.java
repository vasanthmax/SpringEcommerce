package com.vasanth.Ecommerce_Backend.repo;

import com.vasanth.Ecommerce_Backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepo extends JpaRepository<Cart, UUID> {

    Cart findByUserUserId(UUID userId);
}
