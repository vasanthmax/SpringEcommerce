package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartRepo extends JpaRepository<Cart, UUID> {

    Cart findByUserUserId(UUID userId);

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 and c.id = ?2 ")
    Cart findCartByEmailandCartId(String email, UUID cartId);
}
