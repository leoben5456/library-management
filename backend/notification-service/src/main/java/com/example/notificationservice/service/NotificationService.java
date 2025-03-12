package com.example.notificationservice.service;


import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.UsersNotifications;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    void AddNotificaiton(Notification notification,String email);

    List<Notification> loadUserUndreadNotifications(String token);
    void markNotificationAsRead(String token);


}
