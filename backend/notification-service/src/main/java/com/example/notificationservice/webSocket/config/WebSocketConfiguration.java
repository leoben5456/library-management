package com.example.notificationservice.webSocket.config;


import com.example.notificationservice.service.JwtUtil;
import com.example.notificationservice.webSocket.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

     private final JwtUtil jwtUtil;



    public WebSocketConfiguration(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config
                .enableStompBrokerRelay("/queue", "/topic","/exchange")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("user")
                .setClientPasscode("password")
                .setSystemLogin("user")
                .setSystemPasscode("password")
                .setTaskScheduler(heartbeatTaskScheduler());


        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200")
                .addInterceptors(new JwtInterceptor(jwtUtil))
                .setHandshakeHandler(new DefaultHandshakeHandler() {

                    //This part of code change the default identifier of stomp sessions,so message can be sent to specific user and routed by mails.
                    @Override
                    protected Principal determineUser(ServerHttpRequest request,
                                                      WebSocketHandler wsHandler,
                                                      Map<String, Object> attributes) {
                        String email = (String) attributes.get("email");
                        if (email == null) {
                            // fallback or reject handshake
                            return null;
                        }
                        return new Principal() {
                            @Override
                            public String getName() {
                                return email;
                            }
                        };
                    }
                });


    }

    @Bean
    public ThreadPoolTaskScheduler heartbeatTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("stomp-heartbeat-");
        taskScheduler.initialize();
        return taskScheduler;
    }
}
