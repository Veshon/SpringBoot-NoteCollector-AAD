package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dao;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.entity.impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDAO extends JpaRepository<NoteEntity, String > {
}
