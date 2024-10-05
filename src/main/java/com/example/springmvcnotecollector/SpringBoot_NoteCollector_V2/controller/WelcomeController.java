package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/welcome")

public class WelcomeController {

@GetMapping
    public String viewWelcomeScreen(Model model){
        model.addAttribute("message","NoteCollector");
        return "Welcome";
    }
}
