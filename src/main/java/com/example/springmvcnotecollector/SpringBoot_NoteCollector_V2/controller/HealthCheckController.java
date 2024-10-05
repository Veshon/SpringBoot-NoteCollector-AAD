package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")

public class HealthCheckController {
    @PostMapping
    public String healthTest(){
        return "Note controller is working";
    }
}
