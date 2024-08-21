package com.vasanth.EcommerceBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private UUID orderItemId;
    private ProductDTO product;
    private int quantity;
    private double discount;
    private double orderedProductPrice;
}
