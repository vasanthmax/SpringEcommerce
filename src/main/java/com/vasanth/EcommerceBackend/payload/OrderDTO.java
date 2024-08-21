package com.vasanth.EcommerceBackend.payload;

import com.vasanth.EcommerceBackend.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private UUID orderId;
    private String email;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private LocalDate orderDate;
    private PaymentDTO payment;
    private double totalAmount;
    private String orderStatus;
}
