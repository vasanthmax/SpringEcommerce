package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {
}
