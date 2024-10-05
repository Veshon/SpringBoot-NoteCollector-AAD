package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.impl;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDTO implements NoteStatus {

    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String createdDate;
    private String priorityLevel;
    private String userId;
}

