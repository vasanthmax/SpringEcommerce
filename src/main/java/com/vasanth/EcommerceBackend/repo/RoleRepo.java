package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    Role findByRoleName(String string);
}
