package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUserName(String token);
    String generateToken(UserDetails user);
    boolean validateToken(String token,UserDetails userDetails);
    String refreshToken(String prevToken);
}
