package com.vasanth.EcommerceBackend.controller;

import com.vasanth.EcommerceBackend.config.AppConstants;
import com.vasanth.EcommerceBackend.payload.OrderDTO;
import com.vasanth.EcommerceBackend.payload.OrderResponse;
import com.vasanth.EcommerceBackend.service.JWTService;
import com.vasanth.EcommerceBackend.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/public/users/cart/payment/{paymentMethod}/order")
    public ResponseEntity<OrderDTO> orderProducts(HttpServletRequest request, @PathVariable("paymentMethod") String paymentMethod){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        OrderDTO orderDTO = orderService.orderProducts(emailId, paymentMethod);

        return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.CREATED);

    }

    @GetMapping("/admin/orders")
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_ORDER_BY,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){

        OrderResponse orderResponse = orderService.getAllOrders(pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);

    }

    @GetMapping("/public/users/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(HttpServletRequest request){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        List<OrderDTO> orderDTOS = orderService.getOrdersByUser(emailId);

        return new ResponseEntity<List<OrderDTO>>(orderDTOS,HttpStatus.OK);

    }

    @GetMapping("/public/users/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByUser(HttpServletRequest request,@PathVariable("orderId") UUID orderId){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        OrderDTO orderDTO = orderService.getOrderByUser(emailId,orderId);

        return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/users/orders/{orderId}/orderStatus/{orderStatus}")
    public ResponseEntity<OrderDTO> updateOrder(HttpServletRequest request,@PathVariable("orderId") UUID orderId,@PathVariable("orderStatus") String orderStatus){
        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        OrderDTO orderDTO = orderService.updateOrder(emailId,orderId,orderStatus);

        return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.OK);

    }
}
