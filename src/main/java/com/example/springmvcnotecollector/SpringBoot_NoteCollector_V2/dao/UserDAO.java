package com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.dao;

import com.example.springmvcnotecollector.SpringBoot_NoteCollector_V2.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

}
