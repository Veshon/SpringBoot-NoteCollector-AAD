package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dao.UserDAO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.entity.impl.UserEntity;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.UserNotFoundException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.JWTAuthResponse;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure.SignIn;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.AuthService;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.JWTService;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthServiceIMPL implements AuthService {

    private final UserDAO userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        var generatedToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        UserEntity savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUserName(accessToken);
        var findUser = userDAO.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        var refreshToken = jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
