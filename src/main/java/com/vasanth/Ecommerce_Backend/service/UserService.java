package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.config.AppConstants;
import com.vasanth.Ecommerce_Backend.exceptions.APIException;
import com.vasanth.Ecommerce_Backend.model.Role;
import com.vasanth.Ecommerce_Backend.model.User;
import com.vasanth.Ecommerce_Backend.payload.CommonMapper;
import com.vasanth.Ecommerce_Backend.payload.UserDTO;
import com.vasanth.Ecommerce_Backend.repo.RoleRepo;
import com.vasanth.Ecommerce_Backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    public UserDTO registerUser(User user) {

        User savedUser = userRepo.findByEmail(user.getEmail());

        if(savedUser != null){
            throw new APIException("User Already exists with email " + user.getEmail());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByRoleName(AppConstants.ROLE_USER);
        user.getRoles().add(role);

        User newuser = userRepo.save(user);

        return CommonMapper.INSTANCE.toUserDTO(newuser);

    }
}
