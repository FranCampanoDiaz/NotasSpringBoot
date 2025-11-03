package com.example.notes.controller;

import com.example.notes.model.AppUser;
import com.example.notes.model.Note;
import com.example.notes.repository.service.AppUserRepository;
import com.example.notes.repository.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/notas")
public class NoteController {

    private final NoteService noteService;
    private final AppUserRepository userRepository;

    @Autowired
    public NoteController(NoteService noteService, AppUserRepository userRepository) {
        this.noteService = noteService;
        this.userRepository = userRepository;
    }

//    @GetMapping
//    public ResponseEntity<List<Note>> findAll() {
//        return ResponseEntity.ok(noteService.findAll());
//    }

    @GetMapping
    public ResponseEntity<List<Note>> findAllByUser(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(noteService.findAllByUser(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(noteService.findByIdForUser(id, username));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotes(
            @RequestParam("title") String title,
            Principal principal) {

        AppUser user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Note> notes = noteService.searchByTitle(user.getId(), title);
        return ResponseEntity.ok(notes);
    }

//    @PostMapping
//    public ResponseEntity<Note> save(@RequestBody Note note) {
//        return ResponseEntity.ok(noteService.save(note));
//    }

    @PostMapping
    public ResponseEntity<Note> save(@RequestBody Note note, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(noteService.saveForUser(note, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody Note noteActualizada, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(noteService.updateNote(id, noteActualizada, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        noteService.deleteById(id, username);
        return ResponseEntity.noContent().build();
    }
}
