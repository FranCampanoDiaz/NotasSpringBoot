package com.example.notes.repository.service;

import com.example.notes.model.AppUser;
import com.example.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContainingIgnoreCase(String title);

    List<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

    List<Note> findByUser(AppUser user);
}
