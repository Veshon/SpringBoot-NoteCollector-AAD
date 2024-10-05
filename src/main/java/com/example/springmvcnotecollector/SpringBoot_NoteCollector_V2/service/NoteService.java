package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.NoteStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    void saveNote(NoteDTO noteDTO);
    List<NoteDTO> getAllNotes();
    NoteStatus getNote(String noteId);
    void deleteNote(String noteId);
    void updateNote(String noteId, NoteDTO noteDTO);

}
