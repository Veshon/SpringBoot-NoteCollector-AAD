package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.JWTAuthResponse;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.SignIn;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthServiceIMPL implements AuthService {

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        return null;
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        return null;
    }
}
