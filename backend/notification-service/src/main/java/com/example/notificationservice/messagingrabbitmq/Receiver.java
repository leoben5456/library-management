package com.example.notificationservice.messagingrabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Receiver {

    private final SimpMessagingTemplate messagingTemplate;
    private  final ObjectMapper objectMapper;

    public Receiver(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotification(Map<String, String> notification) {
        String email = notification.get("email");
        String message = notification.get("message");

        System.out.println("Received notification for: " + email + " -> " + message);

        // Send message to the correct user via STOMP
        messagingTemplate.convertAndSendToUser(email, "/queue/notification", message);
    }


}
