package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dao.UserDAO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.UserStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.entity.impl.UserEntity;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.DataPersistException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.UserNotFoundException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.UserService;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class UserServiceIMPL implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        if (savedUser == null) {
            throw new DataPersistException("User not saved");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> allUsers = userDAO.findAll();
        return mapping.asUserDTOList(allUsers);
    }

    @Override
    public UserStatus getUser(String userId) {

        if (userDAO.existsById(userId)){
            UserEntity selectedUser = userDAO.getReferenceById(userId);
            return mapping.toUserDTO(selectedUser);
        }
        return new SelectedUserAndNoteErrorStatus(2, "User with id " + userId + " not found");
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser = userDAO.findById(userId);

        if (!existedUser.isPresent()){
            throw new UserNotFoundException("User id" + userId + "Not found");
        }else {
            userDAO.deleteById(userId);
        }
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDAO.findById(userId); // optional cuz to reduce null point exception
        if (tmpUser.isPresent()){
            tmpUser.get().setFirstName(userDTO.getFirstName());
            tmpUser.get().setLastName(userDTO.getLastName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setProfilePic(userDTO.getProfilePic());
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userName ->
                userDAO.findByEmail(userName)
                        .orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
}
