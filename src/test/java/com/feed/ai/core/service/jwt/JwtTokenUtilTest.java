package com.feed.ai.core.service.jwt;

import com.feed.ai.core.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtTokenUtilTest {

    @Mock
    private User user;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        String jwtSecret = "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
        jwtTokenUtil = new JwtTokenUtil(jwtSecret);
    }


    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void generateJwtToken_ShouldReturnToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        String subject = "aashish";

        String token = jwtTokenUtil.generateJwtToken(claims, subject);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void validateToken_ShouldReturnTrue_ForValidToken() {
        String username = "aashish";
        when(user.getUsername()).thenReturn(username);

        Map<String, String> claims = new HashMap<>();
        String token = jwtTokenUtil.generateJwtToken(claims, username);

        assertTrue(jwtTokenUtil.validateToken(token, user));
    }

    @Test
    void validateToken_ShouldReturnFalse_ForInvalidToken() {
        when(user.getUsername()).thenReturn("aashish");

        Map<String, String> claims = new HashMap<>();
        String token = jwtTokenUtil.generateJwtToken(claims, "karki");

        assertFalse(jwtTokenUtil.validateToken(token, user));
    }

    @Test
    void extractClaims_ShouldReturnCorrectUsername() {
        String username = "aashish";
        Map<String, String> claims = new HashMap<>();
        String token = jwtTokenUtil.generateJwtToken(claims, username);

        Claims extractedClaims = jwtTokenUtil.extractAllClaims(token);
        assertEquals(username, extractedClaims.getSubject());
    }
}
