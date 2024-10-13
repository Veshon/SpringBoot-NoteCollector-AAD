package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.UserStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);
    UserDetailsService userDetailsService();

}
