package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.JWTAuthResponse;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}
