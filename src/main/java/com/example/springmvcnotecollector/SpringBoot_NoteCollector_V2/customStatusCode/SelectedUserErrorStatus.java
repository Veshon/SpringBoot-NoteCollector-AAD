package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.customStatusCode;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SelectedUserErrorStatus implements UserStatus {
    private int statusCode;
    private String statusMessage;
}
