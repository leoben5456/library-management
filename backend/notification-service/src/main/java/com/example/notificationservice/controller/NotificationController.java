package com.example.notificationservice.controller;


import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.NotificationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {


    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return new ResponseEntity<>("test good", HttpStatus.OK);
    }

    @GetMapping("/sendTestMessage")
    public ResponseEntity<String> sendTestMessage() {
        // Sends a test message to all subscribers of the /topic/test destination
        simpMessagingTemplate.convertAndSend("/topic/test", "Hello, WebSocket!");
        return ResponseEntity.ok("Test message sent to /topic/test");
    }


    @PostMapping("/notify/{email}")
    public String notifyUser(@PathVariable String email) {

        simpMessagingTemplate.convertAndSendToUser(email, "/queue/notification", "hello user this message for you.");
        return "Notification sent to:" + email;
    }

    @GetMapping("/user/unread/notifications/")
    public ResponseEntity<?> getUnreadNotifications(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        try {
            List<Notification> notifications = notificationService.loadUserUndreadNotifications(token.substring(7));
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Try later there's an issue now.");
        }

    }

    @PostMapping("/user/mark/notifications/as/read")
    public ResponseEntity<?> markAsRead(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        HashMap<String,String> response=new HashMap<>();
        try {
            notificationService.markNotificationAsRead(token.substring(7));
            response.put("message","done.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","Try later there's an issue now.");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);

        }
    }


}

