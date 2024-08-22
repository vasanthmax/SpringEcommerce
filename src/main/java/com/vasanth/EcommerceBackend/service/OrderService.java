package com.vasanth.EcommerceBackend.service;

import com.vasanth.EcommerceBackend.exceptions.APIException;
import com.vasanth.EcommerceBackend.exceptions.ResourceNotFoundException;
import com.vasanth.EcommerceBackend.model.*;
import com.vasanth.EcommerceBackend.payload.CommonMapper;
import com.vasanth.EcommerceBackend.payload.OrderDTO;
import com.vasanth.EcommerceBackend.payload.OrderResponse;
import com.vasanth.EcommerceBackend.repo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    public OrderResponse getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Order> pagedOrders = orderRepo.findAll(pageable);

        List<Order> orders = pagedOrders.getContent();

        List<OrderDTO> orderDTOS = orders.stream().map(CommonMapper.INSTANCE::toOrderDTO).toList();

        if (orderDTOS.isEmpty()){
            throw new APIException("No Orders Placed yet");
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(orderDTOS);
        orderResponse.setPageNumber(pagedOrders.getNumber());
        orderResponse.setPageSize(pagedOrders.getSize());
        orderResponse.setTotalElements(pagedOrders.getTotalElements());
        orderResponse.setTotalPages(pagedOrders.getTotalPages());
        orderResponse.setLastPage(pagedOrders.isLast());

        return orderResponse;

    }

    public List<OrderDTO> getOrdersByUser(String emailId) {

        List<Order> orders = orderRepo.findByEmail(emailId);

        List<OrderDTO> orderDTOS = orders.stream().map(CommonMapper.INSTANCE::toOrderDTO).toList();

        if(orderDTOS.isEmpty()){
            throw new APIException("No Orders placed yet by the user " + emailId);
        }

        return orderDTOS;

    }

    public OrderDTO getOrderByUser(String emailId, UUID orderId) {
        Order order = orderRepo.findByEmailAndOrderId(emailId,orderId);

        OrderDTO orderDTO = CommonMapper.INSTANCE.toOrderDTO(order);

        if (orderDTO == null){
            throw new ResourceNotFoundException("Order","orderId",orderId);
        }

        return orderDTO;
    }

    public OrderDTO updateOrder(String emailId, UUID orderId, String orderStatus) {
        Order order = orderRepo.findByEmailAndOrderId(emailId,orderId);

        if (order == null){
            throw new ResourceNotFoundException("Order","orderId",orderId);
        }

        order.setOrderStatus(orderStatus);

        orderRepo.save(order);

        return CommonMapper.INSTANCE.toOrderDTO(order);
    }
}
