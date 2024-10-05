package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.controller;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.customStatusCode.SelectedUserErrorStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.UserStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.UserDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.DataPersistException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.UserNotFoundException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.UserService;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/users")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> saveUser(
                @RequestPart("firstName") String firstName,
                @RequestPart("lastName") String lastName,
                @RequestPart("email") String email,
                @RequestPart("password") String password,
                @RequestPart("profilePic") MultipartFile profilePic){

            // profilePic ----> Base64
            String base64ProPic = "";

            try {
                byte [] bytesProPic = profilePic.getBytes(); //Converting profile pic to byte array
                base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
                String userId = AppUtil.generateUserId(); //Generating UUID

                var buildUserDTO = new UserDTO(); //Creating obj
                buildUserDTO.setUserId(userId);
                buildUserDTO.setFirstName(firstName);
                buildUserDTO.setLastName(lastName);
                buildUserDTO.setEmail(email);
                buildUserDTO.setPassword(password);
                buildUserDTO.setProfilePic(base64ProPic);

                userService.saveUser(buildUserDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable ("userId") String userId){

        String regexForUserId = "^USER-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(userId);

        if (!regexMatcher.matches()){
            return new SelectedUserErrorStatus(1,"User ID is not valid");
        }
        return userService.getUser(userId);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId){

        String regexForUserId = "^USER-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(userId);

        try {

            if (!regexMatcher.matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(
            @RequestPart ("firstName") String firstName,
            @RequestPart ("lastName") String lastName,
            @RequestPart ("email") String email,
            @RequestPart ("password") String password,
            @RequestPart ("profilePic") MultipartFile profilePic,
            @PathVariable ("userId") String userId
    ){
        // profilePic ----> Base64
        String base64ProPic = "";
        try {
            byte [] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Build the Object
        UserDTO buildUserDTO = new UserDTO();
        buildUserDTO.setUserId(userId);
        buildUserDTO.setFirstName(firstName);
        buildUserDTO.setLastName(lastName);
        buildUserDTO.setEmail(email);
        buildUserDTO.setPassword(password);
        buildUserDTO.setProfilePic(base64ProPic);
        userService.updateUser(userId,buildUserDTO);
    }
}
