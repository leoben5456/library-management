package com.example.notificationservice.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "UsersNotifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersNotifications {

    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private int TotalUnreadNotifications;
    private List<Notification> Notifications;

}
