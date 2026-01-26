package com.vardhan.order_service.service;

import com.vardhan.order_service.entity.Order;
import com.vardhan.order_service.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.vardhan.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository,
                        KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(String productId, int quantity) {

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), productId, quantity);

        kafkaTemplate.send("order-created", event);

        System.out.println("OrderCreated event published: " + savedOrder.getId());

        return savedOrder;
    }
}

