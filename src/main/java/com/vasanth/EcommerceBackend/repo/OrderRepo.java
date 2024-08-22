package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    List<Order> findByEmail(String emailId);

    Order findByEmailAndOrderId(String email,UUID orderId);

}
