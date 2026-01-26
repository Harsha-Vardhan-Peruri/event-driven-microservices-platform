package com.vardhan.order_service.controller;

import com.vardhan.order_service.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vardhan.order_service.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order request) {
        Order order = orderService.createOrder(
                request.getProductId(),
                request.getQuantity()
        );
        return ResponseEntity.ok(order);
    }
}

