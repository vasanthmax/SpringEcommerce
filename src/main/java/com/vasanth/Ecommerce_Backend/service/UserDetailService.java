package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.config.UserPrincipal;
import com.vasanth.Ecommerce_Backend.exceptions.ResourceNotFoundException;
import com.vasanth.Ecommerce_Backend.model.User;
import com.vasanth.Ecommerce_Backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);

        if (user == null){
            throw new ResourceNotFoundException("User","email",username);
        }

        return new UserPrincipal(user);
    }
}
