package com.vardhan.inventory_service.consumer;

import com.vardhan.inventory_service.event.OrderCreatedEvent;
import com.vardhan.inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class OrderEventConsumer {

    private final InventoryService inventoryService;
    private final ExecutorService executorService;

    public OrderEventConsumer(InventoryService inventoryService, ExecutorService executorService) {
        this.inventoryService = inventoryService;
        this.executorService = executorService;
    }

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consume(OrderCreatedEvent event){
        executorService.submit(() -> {
            inventoryService.reduceStock(
                    event.getProductId(),
                    event.getQuantity()
            );
        });
    }
}
