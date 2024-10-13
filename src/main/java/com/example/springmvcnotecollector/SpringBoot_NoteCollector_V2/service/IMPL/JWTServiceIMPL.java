package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.JWTService;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTServiceIMPL implements JWTService {
    @Override
    public String extractUserName(String token) {
        return "";
    }
    @Override
    public String generateToken(UserDetails user) {
        return "";
    }
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }
    @Override
    public String refreshToken(String prevToken) {
        return "";
    }
}
