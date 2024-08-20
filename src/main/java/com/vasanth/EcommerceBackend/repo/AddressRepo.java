package com.vasanth.EcommerceBackend.repo;

import com.vasanth.EcommerceBackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepo extends JpaRepository<Address, UUID> {

    Address findByAddressIdAndUserUserId(UUID addressId, UUID userId);

    List<Address> findByUserUserId(UUID userId);
}
