package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepo extends JpaRepository<OrderItem, UUID> {
}
