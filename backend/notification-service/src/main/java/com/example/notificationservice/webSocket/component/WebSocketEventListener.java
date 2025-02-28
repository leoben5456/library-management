package com.example.notificationservice.webSocket.component;


import com.example.notificationservice.service.JwtUtil;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class WebSocketEventListener {


    private final JwtUtil jwtUtil;

    public WebSocketEventListener( JwtUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Session ID:"+sessionId);
        String token = (String) headerAccessor.getSessionAttributes().get("token");
        String email = (String) headerAccessor.getSessionAttributes().get("email");

        try {
            if (token != null && email != null) {

                logger.info("WebSocket session stored: {} for user: {}", sessionId, email);
            } else {
                logger.warn("Token or email not found in session attributes.");
            }
        } catch (Exception e) {
            logger.error("Error in handleWebSocketConnectListener: ", e);

            e.printStackTrace();

        }
    }




    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();



        System.out.println("WebSocket session removed: " + sessionId);
    }



}