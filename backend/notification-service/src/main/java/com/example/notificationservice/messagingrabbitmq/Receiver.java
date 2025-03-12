package com.example.notificationservice.messagingrabbitmq;


import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Receiver {

    private final SimpMessagingTemplate messagingTemplate;
    private  final NotificationService notificationService;
    public Receiver(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;

        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotification(Map<String, String> notification) {
        String email = notification.get("email");
        String message = notification.get("message");

        // Add notification to the database
        Notification notificationData = new Notification();
        notificationData.setMessage(message);
        notificationData.setStatus("unread");
        notificationData.setType("notification");
        notificationService.AddNotificaiton(notificationData, email);
        System.out.println("Received notification for: " + email + " -> " + message);

        // Send message to the correct user via STOMP
        messagingTemplate.convertAndSendToUser(email, "/exchange/amq.direct/notification", message);
    }



}
