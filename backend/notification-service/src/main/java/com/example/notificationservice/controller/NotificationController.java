package com.example.notificationservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {


    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){

        return  new ResponseEntity<>("test good", HttpStatus.OK);
    }

    @GetMapping("/sendTestMessage")
    public ResponseEntity<String> sendTestMessage() {
        // Sends a test message to all subscribers of the /topic/test destination
        simpMessagingTemplate.convertAndSend("/topic/test", "Hello, WebSocket!");
        return ResponseEntity.ok("Test message sent to /topic/test");
    }



    @PostMapping("/notify/{email}")
    public  String notifyUser(@PathVariable String email){

        simpMessagingTemplate.convertAndSendToUser(email,"/queue/notification","hello user this message for you.");
        return "Notification sent to:"+email;
    }


}
