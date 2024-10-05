package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootNoteCollectorV2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootNoteCollectorV2Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

}
