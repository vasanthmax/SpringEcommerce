package com.vasanth.Ecommerce_Backend.repo;

import com.vasanth.Ecommerce_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
