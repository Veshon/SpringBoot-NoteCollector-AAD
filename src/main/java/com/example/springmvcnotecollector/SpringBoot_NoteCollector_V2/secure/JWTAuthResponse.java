package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class JWTAuthResponse {

    private String token;

}
