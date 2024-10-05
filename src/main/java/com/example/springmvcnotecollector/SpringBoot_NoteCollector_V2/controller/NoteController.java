package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.controller;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.NoteStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.NoteDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.DataPersistException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.NoteNotFoundException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/notes")

public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) {
            try {
                noteService.saveNote(noteDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistException e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping(value = "/{noteID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable ("noteID") String noteId){
        String regexForUserID = "^NOTE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        if (!regexMatcher.matches()) {
            return new SelectedUserAndNoteErrorStatus(1,"Note ID is not ");
        }
        return noteService.getNote(noteId);
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable ("noteId") String noteId){
        String regexForUserID = "^NOTE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        try {
            if (!regexMatcher.matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.deleteNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{noteId}")
    public ResponseEntity<Void> updateNote(@PathVariable ("noteId") String noteId,
                                           @RequestBody NoteDTO updatedNoteDTO){
        String regexForUserID = "^NOTE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        try {
            if(!regexMatcher.matches() || updatedNoteDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateNote(noteId,updatedNoteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
