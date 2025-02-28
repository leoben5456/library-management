package com.example.notificationservice.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    @KafkaListener(topics ="notification-topic" ,groupId = "notification-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
