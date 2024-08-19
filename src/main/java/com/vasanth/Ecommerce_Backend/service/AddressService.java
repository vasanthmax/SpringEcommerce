package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.exceptions.ResourceNotFoundException;
import com.vasanth.Ecommerce_Backend.model.Address;
import com.vasanth.Ecommerce_Backend.model.User;
import com.vasanth.Ecommerce_Backend.payload.AddressDTO;
import com.vasanth.Ecommerce_Backend.payload.CommonMapper;
import com.vasanth.Ecommerce_Backend.repo.AddressRepo;
import com.vasanth.Ecommerce_Backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    public AddressDTO createAddress(String emailId, Address address) {

        User user = userRepo.findByEmail(emailId);

        if(user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Address newAddress = new Address();
        newAddress.setBuildingName(address.getBuildingName());
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        newAddress.setPincode(address.getPincode());
        newAddress.setCountry(address.getCountry());
        newAddress.setUser(user);

        addressRepo.save(newAddress);

        return CommonMapper.INSTANCE.toAddressDTO(newAddress);
    }

    public List<AddressDTO> getAddresses() {

        List<Address> addresses = addressRepo.findAll();

        List<AddressDTO> addressDTOS = addresses.stream().map(CommonMapper.INSTANCE::toAddressDTO).toList();

        return addressDTOS;

    }
}
