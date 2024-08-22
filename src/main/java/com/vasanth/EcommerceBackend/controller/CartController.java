package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.payload.CartDTO;
import com.vasanth.EcommerceBackend.service.CartService;
import com.vasanth.EcommerceBackend.service.JWTService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce")
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

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){
        List<CartDTO> cartDTOS = cartService.getCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOS,HttpStatus.OK);
    }

    @GetMapping("/public/user/cart")
    public ResponseEntity<CartDTO> getCartById(HttpServletRequest request){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        CartDTO cartDTO = cartService.getCartById(emailId);

        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);

    }


    @PutMapping("/public/cart/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> updateCartProduct(HttpServletRequest request,@PathVariable("productId") UUID productId,@PathVariable("quantity") int quantity){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        CartDTO cartDTO = cartService.updateProductQuantity(emailId,productId,quantity);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @DeleteMapping("/public/cart/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(HttpServletRequest request,@PathVariable("productId") UUID productId){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        String response = cartService.deleteProductFromCart(emailId,productId);

        return new ResponseEntity<String>(response,HttpStatus.OK);

    }


}
