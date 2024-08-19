package com.vasanth.Ecommerce_Backend.service;

import com.vasanth.Ecommerce_Backend.exceptions.APIException;
import com.vasanth.Ecommerce_Backend.exceptions.ResourceNotFoundException;
import com.vasanth.Ecommerce_Backend.model.Cart;
import com.vasanth.Ecommerce_Backend.model.CartItem;
import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.model.User;
import com.vasanth.Ecommerce_Backend.payload.CartDTO;
import com.vasanth.Ecommerce_Backend.payload.CommonMapper;
import com.vasanth.Ecommerce_Backend.payload.ProductDTO;
import com.vasanth.Ecommerce_Backend.repo.CartItemRepo;
import com.vasanth.Ecommerce_Backend.repo.CartRepo;
import com.vasanth.Ecommerce_Backend.repo.ProductRepo;
import com.vasanth.Ecommerce_Backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemRepo cartItemRepo;



    public CartDTO addProductToCart(String userEmail, UUID productId, int quantity) {
        User user = userRepo.findByEmail(userEmail);

        if(user == null){
            throw new ResourceNotFoundException("User","email",userEmail);
        }

        Cart cart = cartRepo.findByUserUserId(user.getUserId());

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        CartItem cartItem = cartItemRepo.findCartItemByProductIAndCartId(cart.getCartId(),productId);

        if(cartItem != null){
            throw new APIException("Product" + product.getProductName() + "already exists in the cart");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + "is not available");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please make an order" + product.getProductName() +
                    "less than or equal to the quantity"+ product.getQuantity());
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setSpecialPrice(product.getSpecialPrice());

        cartItemRepo.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + (newCartItem.getSpecialPrice() * quantity));

        productRepo.save(product);
        cartRepo.save(cart);

        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(cart);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(p -> CommonMapper.INSTANCE.toProductDTO(p.getProduct())).toList();


        cartDTO.setProducts(productDTOS);



        return cartDTO;
    }

    
}
