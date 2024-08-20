package com.vasanth.EcommerceBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private UUID categoryId;
    private String categoryName;

}
