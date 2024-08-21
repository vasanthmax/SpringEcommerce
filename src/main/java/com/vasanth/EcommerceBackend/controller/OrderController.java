package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.payload.OrderDTO;
import com.vasanth.EcommerceBackend.service.JWTService;
import com.vasanth.EcommerceBackend.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/public/users/cart/payment/{paymentMethod}/order")
    public ResponseEntity<OrderDTO> orderProducts(HttpServletRequest request, @PathVariable("paymentMethod") String paymentMethod){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        OrderDTO orderDTO = orderService.orderProducts(emailId, paymentMethod);

        return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.CREATED);

    }

}
