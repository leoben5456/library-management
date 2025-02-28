package com.example.notificationservice.webSocket.interceptor;

import com.example.notificationservice.service.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class JwtInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        UriComponents uriComponents = UriComponentsBuilder.fromUri(request.getURI()).build();
        String token = uriComponents.getQueryParams().getFirst("token");

        System.out.println("token: " + token);

        if (token == null || token.isEmpty()) { // No need to check for "Bearer " here
            System.out.println("Token is missing in the query parameter.");
            return false;
        }



        if (!jwtUtil.validateToken(token) || jwtUtil.isTokenExpired(token)) {
            System.out.println("Token is invalid or expired.");
            return false;
        }

        try {
            String email = jwtUtil.extractUserEmail(token);
            attributes.put("token", token);
            attributes.put("email", email);
        } catch (Exception e) {
            System.out.println("Error extracting email from token: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        System.out.println("After Handshake executed.");
        if (exception != null) {
            System.err.println("Exception in afterHandshake: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}