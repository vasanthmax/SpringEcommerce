package com.vasanth.Ecommerce_Backend.repo;

import com.vasanth.Ecommerce_Backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 and ci.product.id = ?2")
    CartItem findCartItemByProductIAndCartId(UUID cartId,UUID productId);
}
