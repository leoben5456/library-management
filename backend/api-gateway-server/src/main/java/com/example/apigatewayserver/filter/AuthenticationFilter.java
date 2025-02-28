package com.example.apigatewayserver.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        System.out.println("path: " + path);


        // Bypass filter for auth-service and other specific paths
        if (path.startsWith("/auth-service") ||
                path.equals("/user-service/verify-credentials") ||
                path.equals("/user-service/user/inscription") ||
                path.startsWith("/livre-service/uploads/book-cover")) {
            return chain.filter(exchange);
        }

        // Check if the connection is a WebSocket upgrade request
        String upgradeHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.UPGRADE);

        System.out.println("Header:"+upgradeHeader);
        if (upgradeHeader != null && "websocket".equalsIgnoreCase(upgradeHeader)) {
            // Log all headers for debugging
            exchange.getRequest().getHeaders().forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });
            // Retrieve token from query parameters for WebSocket connections
            String token = exchange.getRequest().getQueryParams().getFirst("token");
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Missing token in query parameters for WebSocket connection");
            }
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                if (isTokenExpired(claims)) {
                    throw new RuntimeException("JWT token has expired");
                }

                // Optionally add the user information to the request headers
                exchange.getRequest().mutate()
                        .header("user", claims.getSubject())
                        .build();
            } catch (ExpiredJwtException e) {
                throw new RuntimeException("JWT token has expired", e);
            } catch (MalformedJwtException e) {
                throw new RuntimeException("Malformed JWT token", e);
            } catch (SignatureException e) {
                throw new RuntimeException("Invalid JWT signature", e);
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token", e);
            }
            return chain.filter(exchange);
        } else {
            // For non-WebSocket requests, check the Authorization header
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                if (isTokenExpired(claims)) {
                    throw new RuntimeException("JWT token has expired");
                }

                // Optionally add the user information to the request headers
                exchange.getRequest().mutate()
                        .header("user", claims.getSubject())
                        .build();
            } catch (ExpiredJwtException e) {
                throw new RuntimeException("JWT token has expired", e);
            } catch (MalformedJwtException e) {
                throw new RuntimeException("Malformed JWT token", e);
            } catch (SignatureException e) {
                throw new RuntimeException("Invalid JWT signature", e);
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token", e);
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
