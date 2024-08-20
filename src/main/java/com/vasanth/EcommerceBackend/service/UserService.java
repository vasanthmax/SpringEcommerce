package com.vasanth.EcommerceBackend.service;

import com.vasanth.EcommerceBackend.config.AppConstants;
import com.vasanth.EcommerceBackend.exceptions.APIException;
import com.vasanth.EcommerceBackend.exceptions.ResourceNotFoundException;
import com.vasanth.EcommerceBackend.model.Cart;
import com.vasanth.EcommerceBackend.model.CartItem;
import com.vasanth.EcommerceBackend.model.Role;
import com.vasanth.EcommerceBackend.model.User;
import com.vasanth.EcommerceBackend.payload.*;
import com.vasanth.EcommerceBackend.repo.CartRepo;
import com.vasanth.EcommerceBackend.repo.RoleRepo;
import com.vasanth.EcommerceBackend.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private JWTService jwtService;

    public UserDTO registerUser(User user) {

        User savedUser = userRepo.findByEmail(user.getEmail());

        Cart cart = new Cart();
        user.setCart(cart);

        if(savedUser != null){
            throw new APIException("User Already exists with email " + user.getEmail());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByRoleName(AppConstants.ROLE_USER);
        user.getRoles().add(role);

        User newuser = userRepo.save(user);

        cart.setUser(newuser);
        cartRepo.save(cart);

        return CommonMapper.INSTANCE.toUserDTO(newuser);

    }

    public UserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<User> pagedUser = userRepo.findAll(pageable);

        List<User> users = pagedUser.getContent();

        if(users.size() == 0){
            throw new APIException("No User exists");
        }

        List<UserDTO> userDTOS = users.stream().map(user -> {
            UserDTO dto = CommonMapper.INSTANCE.toUserDTO(user);

            CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(user.getCart());

            List<ProductDTO> productDTOS = user.getCart().getCartItems().stream()
                    .map(item -> CommonMapper.INSTANCE.toProductDTO(item.getProduct())).toList();

            Set<RoleDTO> roleDTOS = user.getRoles().stream()
                    .map(CommonMapper.INSTANCE::toRoleDTO).collect(Collectors.toSet());


            List<AddressDTO> addressDTOS = user.getAddresses().stream().map(
                    address -> CommonMapper.INSTANCE.toAddressDTO(address)
            ).toList();

            dto.setCartDTO(cartDTO);
            dto.getCartDTO().setProducts(productDTOS);
            dto.setRoles(roleDTOS);
            dto.setAddress(addressDTOS);

            return dto;

        }).toList();

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTOS);
        userResponse.setPageNumber(pagedUser.getNumber());
        userResponse.setPageSize(pagedUser.getSize());
        userResponse.setTotalPages(pagedUser.getTotalPages());
        userResponse.setTotalElements(pagedUser.getTotalElements());
        userResponse.setLastPage(pagedUser.isLast());

        
        return userResponse;

    }

    public UserDTO getUser(HttpServletRequest request) {

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);


        User user = userRepo.findByEmail(emailId);

        if(user == null){
            throw new ResourceNotFoundException("User","email",emailId);
        }

        UserDTO userDTO = CommonMapper.INSTANCE.toUserDTO(user);

        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(user.getCart());

        List<ProductDTO> productDTOS = user.getCart().getCartItems().stream()
                .map(item -> CommonMapper.INSTANCE.toProductDTO(item.getProduct())).toList();

        Set<RoleDTO> roleDTOS = user.getRoles().stream()
                .map(CommonMapper.INSTANCE::toRoleDTO).collect(Collectors.toSet());
        List<AddressDTO> addressDTOS = user.getAddresses().stream().map(
                address -> CommonMapper.INSTANCE.toAddressDTO(address)
        ).toList();
        userDTO.setCartDTO(cartDTO);
        userDTO.getCartDTO().setProducts(productDTOS);
        userDTO.setRoles(roleDTOS);
        userDTO.setAddress(addressDTOS);

        return userDTO;
    }


    public UserDTO updateUser(UserDTO dto, HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNumber(dto.getMobileNumber());

        userRepo.save(user);

        UserDTO userDTO = CommonMapper.INSTANCE.toUserDTO(user);

        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(user.getCart());

        List<ProductDTO> productDTOS = user.getCart().getCartItems().stream()
                .map(item -> CommonMapper.INSTANCE.toProductDTO(item.getProduct())).toList();

        Set<RoleDTO> roleDTOS = user.getRoles().stream()
                .map(CommonMapper.INSTANCE::toRoleDTO).collect(Collectors.toSet());

        List<AddressDTO> addressDTOS = user.getAddresses().stream().map(
                address -> CommonMapper.INSTANCE.toAddressDTO(address)
        ).toList();

        userDTO.setCartDTO(cartDTO);
        userDTO.getCartDTO().setProducts(productDTOS);
        userDTO.setRoles(roleDTOS);
        userDTO.setAddress(addressDTOS);

        return userDTO;

    }


    public String deleteUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        List<CartItem> cartItems = user.getCart().getCartItems();
        UUID cartId = user.getCart().getCartId();

        return  "User Deleted Successfully";
    }
}
