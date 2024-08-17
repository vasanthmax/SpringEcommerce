package com.vasanth.Ecommerce_Backend.controller;

import com.vasanth.Ecommerce_Backend.payload.CartDTO;
import com.vasanth.Ecommerce_Backend.service.CartService;
import com.vasanth.Ecommerce_Backend.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/public/cart/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(HttpServletRequest request, @PathVariable UUID productId, @PathVariable int quantity){
        String token = jwtService.extractToken(request);
        String userEmail = jwtService.extractUserName(token);

        CartDTO cartDTO = cartService.addProductToCart(userEmail,productId,quantity);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);

    }


}
