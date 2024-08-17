package com.vasanth.Ecommerce_Backend.payload;

import com.vasanth.Ecommerce_Backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private UUID cartId;
    private double totalPrice = 0.0;
    private List<ProductDTO> products = new ArrayList<>();
}
