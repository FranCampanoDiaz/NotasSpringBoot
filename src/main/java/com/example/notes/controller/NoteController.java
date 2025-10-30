package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.repository.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/notas")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAll() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(noteService.searchByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Note> save(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.save(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody Note noteActualizada) {
        return ResponseEntity.ok(noteService.updateNote(id, noteActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
