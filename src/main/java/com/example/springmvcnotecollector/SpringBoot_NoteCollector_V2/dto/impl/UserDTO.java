package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserStatus {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePic;
    private List<NoteDTO> notes;
}

