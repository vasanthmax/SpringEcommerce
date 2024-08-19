package com.vasanth.Ecommerce_Backend.controller;

import com.vasanth.Ecommerce_Backend.model.Address;
import com.vasanth.Ecommerce_Backend.payload.AddressDTO;
import com.vasanth.Ecommerce_Backend.repo.UserRepo;
import com.vasanth.Ecommerce_Backend.service.AddressService;
import com.vasanth.Ecommerce_Backend.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressService addressService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/public/address")
    public ResponseEntity<AddressDTO> createAddress(HttpServletRequest request, @Valid @RequestBody Address address){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        AddressDTO addressDTO = addressService.createAddress(emailId,address);

        return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/admin/address")
    public ResponseEntity<List<AddressDTO>> getAddresses(){

        List<AddressDTO> addressDTOS = addressService.getAddresses();

        return new ResponseEntity<List<AddressDTO>>(addressDTOS,HttpStatus.OK);
    }


}
