package com.example.notes.repository.service.service;

import com.example.notes.exception.NoteNotFoundException;
import com.example.notes.exception.NoteNotSaveException;
import com.example.notes.model.Note;
import com.example.notes.repository.service.NoteRepository;
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
        if (note == null) {
            throw new NoteNotSaveException("Service: Note not saved");
        }
        return noteRepository.save(note);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }


    public List<Note> searchByTitle(String title) {
        if (title == null) {
            throw new NoteNotSaveException("Service: Title not found");
        }
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

}
