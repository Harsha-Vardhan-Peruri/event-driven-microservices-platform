package com.vardhan.notification_service.consumer;

import com.vardhan.notification_service.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void consume(OrderCreatedEvent event) {
        System.out.println(
                "Notification sent for Order " + event.getOrderId()
        );
    }
}

