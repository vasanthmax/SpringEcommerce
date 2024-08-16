package com.vasanth.Ecommerce_Backend.payload;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentials {

    @Email
    private String email;
    private String password;
}
