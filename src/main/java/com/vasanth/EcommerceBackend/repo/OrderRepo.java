package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
}
