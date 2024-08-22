package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.config.AppConstants;
import com.vasanth.EcommerceBackend.payload.UserDTO;
import com.vasanth.EcommerceBackend.payload.UserResponse;
import com.vasanth.EcommerceBackend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_USER_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){


        UserResponse userResponse = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);

    }

    @GetMapping("/public/user")
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request){
        UserDTO userDTO = userService.getUser(request);

        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }

    @PutMapping("/public/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto, HttpServletRequest request){

        UserDTO user = userService.updateUser(dto,request);

        return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") UUID userId){

        String deleteUser = userService.deleteUser(userId);

        return new ResponseEntity<String>(deleteUser,HttpStatus.OK);
    }

}
