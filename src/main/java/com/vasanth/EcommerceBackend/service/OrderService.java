package com.vasanth.EcommerceBackend.service;

import com.vasanth.EcommerceBackend.exceptions.APIException;
import com.vasanth.EcommerceBackend.exceptions.ResourceNotFoundException;
import com.vasanth.EcommerceBackend.model.*;
import com.vasanth.EcommerceBackend.payload.CommonMapper;
import com.vasanth.EcommerceBackend.payload.OrderDTO;
import com.vasanth.EcommerceBackend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepo productRepo;

    public OrderDTO orderProducts(String email, String paymentMethod) {

        User user = userRepo.findByEmail(email);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",email);
        }

        Cart cart = cartRepo.findByUserUserId(user.getUserId());

        Order order = new Order();
        order.setEmail(user.getEmail());
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted");

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);

        payment = paymentRepo.save(payment);

        order.setPayment(payment);

        Order savedOrder = orderRepo.save(order);

        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()){
            throw new APIException("Cart is Empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem: cartItems){
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrices(cartItem.getSpecialPrice());
            orderItem.setOrder(savedOrder);

            orderItems.add(orderItem);

        }

        orderItems = orderItemRepo.saveAll(orderItems);

        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();

            Product product = item.getProduct();

            cartService.deleteProductFromCartUsingCartId(cart.getCartId(),item.getProduct().getProductId());

            product.setQuantity(product.getQuantity() - quantity);

            productRepo.save(product);
        });

        OrderDTO orderDTO = CommonMapper.INSTANCE.toOrderDTO(order);

        orderItems.forEach(orderItem -> {
            orderDTO.getOrderItems().add(CommonMapper.INSTANCE.toOrderItemDTO(orderItem));
        });

        return orderDTO;
    }
}
