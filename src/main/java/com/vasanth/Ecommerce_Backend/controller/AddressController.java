package com.vasanth.Ecommerce_Backend.controller;

import com.vasanth.Ecommerce_Backend.payload.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    @PostMapping("/public/address")
    public ResponseEntity<AddressDTO> createAddress(){
        return null;
    }
}
