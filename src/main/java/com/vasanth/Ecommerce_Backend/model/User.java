package com.vasanth.Ecommerce_Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Size(min = 5, max = 20, message = "First Name should be within 5 to 20 characters")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "First Name Should not contain any special characters or numbers")
    private String firstName;

    @Size(min = 5, max = 20, message = "Last Name should be within 5 to 20 characters")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Last Name Should not contain any special characters or numbers")
    private String lastName;

    @Size(min = 10, max = 10, message = "Mobile Number must be digits long")
    @Pattern(regexp = "^\\d{10}$",message = "Mobile Number must contain only numbers")
    private String mobileNumber;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.PERSIST})
    @JoinTable(name = "user_address",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "address_id"))
    List<Address> addresses = new ArrayList<>();

}
