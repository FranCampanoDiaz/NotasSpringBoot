package com.example.notes.repository.service;

import com.example.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public List<Note> searchByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

}
