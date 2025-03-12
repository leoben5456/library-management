package com.example.notificationservice.service;


import com.example.notificationservice.exception.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;


    public  Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("ExpiredJwtException");

        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }


    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }


    public  String extractUserEmail(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }

    }



}
