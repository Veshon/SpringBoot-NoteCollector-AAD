package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.IMPL;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dao.NoteDAO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.NoteStatus;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl.NoteDTO;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.entity.impl.NoteEntity;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.DataPersistException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.exception.NoteNotFoundException;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.service.NoteService;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.util.AppUtil;
import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceIMPL implements NoteService {

    @Autowired
    public NoteDAO noteDAO;

    @Autowired
    public Mapping noteMapping;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());
        NoteEntity savedNote =
                noteDAO.save(noteMapping.toNoteEntity(noteDTO));
        if(savedNote == null){
            throw new DataPersistException("Note not saved");
        }
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteMapping.asNoteDTOList( noteDAO.findAll());
    }

    @Override
    public NoteStatus getNote(String noteId) {
        if(noteDAO.existsById(noteId)){
            var selectedUser = noteDAO.getReferenceById(noteId);
            return noteMapping.toNoteDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2, "Selected note not found");
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<NoteEntity> foundNote = noteDAO.findById(noteId);
        if (!foundNote.isPresent()) {
            throw new NoteNotFoundException("Note not found");
        }else {
            noteDAO.deleteById(noteId);
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<NoteEntity> findNote = noteDAO.findById(noteId);
        if (!findNote.isPresent()) {
            throw new NoteNotFoundException("Note not found");
        }else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setCreatedDate(noteDTO.getCreatedDate());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }
}
