package com.vasanth.EcommerceBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    @NotBlank
    @Size(min = 3,message = "Category Name should have atleast 3 characters")
    private String categoryName;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;
}
