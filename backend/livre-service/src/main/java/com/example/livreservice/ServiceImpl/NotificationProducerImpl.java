package com.example.livreservice.ServiceImpl;


import com.example.livreservice.Service.NotificationProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationProducerImpl implements NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendNotification(String email) {
        // Create a JSON-like object
        Map<String, String> notification = new HashMap<>();
        notification.put("email", email);
        notification.put("message", "Your password account was change.");

        System.out.println("Sending message to RabbitMQ: " + notification);
        rabbitTemplate.convertAndSend("notificationQueue", notification);
    }
}
