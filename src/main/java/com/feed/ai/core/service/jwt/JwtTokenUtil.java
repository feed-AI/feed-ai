package com.feed.ai.core.service.jwt;

import com.feed.ai.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Component
public class JwtTokenUtil {

    private final String jwtSecret;

    public JwtTokenUtil(@Value("${jwt.secretKey}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    private SecretKey getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateJwtToken(Map<String, String> claims, String subject) {
        long oneDayExpirationTime = 86400000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + oneDayExpirationTime);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(getSigningKey(jwtSecret))
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey(jwtSecret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
