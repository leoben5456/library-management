package com.example.notificationservice.serviceImpl;


import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.UsersNotifications;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.JwtUtil;
import com.example.notificationservice.service.NotificationService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private  final JwtUtil jwtUtil;
    private final MongoTemplate mongoTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository, JwtUtil jwtUtil, MongoTemplate mongoTemplate) {
        this.notificationRepository = notificationRepository;
        this.jwtUtil = jwtUtil;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void AddNotificaiton(Notification notification, String email) {
        Optional<UsersNotifications> userNotifications = notificationRepository.findByEmail(email);

         System.out.println("status:"+userNotifications.isEmpty());
        if (userNotifications.isEmpty()) {
            // Create new notification object in case user don't have any notification
            UsersNotifications newUserNotificationRecord = new UsersNotifications();
            newUserNotificationRecord.setEmail(email);
            newUserNotificationRecord.setTotalUnreadNotifications(1);

            List<Notification> notifications = new ArrayList<>();
            notifications.add(notification);
            newUserNotificationRecord.setNotifications(notifications);

            // Save the notification
            notificationRepository.save(newUserNotificationRecord);
        } else {

            // Get the existing UsersNotifications object
            UsersNotifications existingUserNotifications = userNotifications.get();

            List<Notification> notifications = existingUserNotifications.getNotifications();
            notifications.add(notification);

            // Update the existing object
            existingUserNotifications.setNotifications(notifications);
            existingUserNotifications.setTotalUnreadNotifications(existingUserNotifications.getTotalUnreadNotifications() + 1);

            // Save the updated object
            notificationRepository.save(existingUserNotifications);
        }
    }

    @Override
    public List<Notification> loadUserUndreadNotifications(String token) {
        String userEmail= jwtUtil.extractUserEmail(token);

        System.out.println("user email:"+userEmail);
        //Create a query
        Query query=new Query();
        query.addCriteria(
                Criteria.where("email").is(userEmail)
                        .and("Notifications").elemMatch(Criteria.where("status").is("unread"))
        );


        List<UsersNotifications> queryResult = mongoTemplate.find(query,UsersNotifications.class);
        // Extract and filter notifications using Streams
        return queryResult.stream()
                .flatMap(userNotif -> userNotif.getNotifications().stream())
                .filter(notification -> "unread".equals(notification.getStatus()))
                .collect(Collectors.toList());




    }

    @Override
    public void markNotificationAsRead(String token) {

        String userEmail=jwtUtil.extractUserEmail(token);

        //Create a query that return unread notifications
        Query query=new Query();
        query.addCriteria(
                Criteria.where("email").is(userEmail)
                        .and("Notifications").elemMatch(Criteria.where("status").is("unread"))
        );

        //Create update to change notifications that already read
        Update update = new Update().set("Notifications.$[].status", "read");

        mongoTemplate.findAndModify(query,update,UsersNotifications.class);






    }


}
