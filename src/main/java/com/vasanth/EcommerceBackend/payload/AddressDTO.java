package com.vasanth.EcommerceBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private UUID addressId;
    private String buildingName;
    private String city;
    private String country;
    private String pincode;
    private String state;
    private String street;
}
