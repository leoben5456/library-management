package com.example.livreservice.Service;


import org.springframework.stereotype.Service;

@Service
public interface NotificationProducer {
    void sendNotification(String email);
}
