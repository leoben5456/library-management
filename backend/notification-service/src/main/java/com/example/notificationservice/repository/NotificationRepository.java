package com.example.notificationservice.repository;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.UsersNotifications;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<UsersNotifications,String> {
    Optional<UsersNotifications> findByEmail(String email);



}
