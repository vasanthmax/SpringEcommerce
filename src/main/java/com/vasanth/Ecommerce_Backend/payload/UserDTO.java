package com.vasanth.Ecommerce_Backend.payload;

import com.vasanth.Ecommerce_Backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private Set<RoleDTO> roles;
    private List<AddressDTO> address;
    private CartDTO cartDTO;
}
