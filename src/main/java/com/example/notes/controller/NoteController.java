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
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    //Obtener todas
    @GetMapping
    public ResponseEntity<List<Note>> findAll() {
        return ResponseEntity.ok(noteService.findAll());
    }

    //Guardar
    @PostMapping
    public ResponseEntity<Note> save(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.save(note));
    }

    //Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> delete(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        if (noteService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(noteService.findById(id));
        }
    }

    //Buscar por titulo
    @GetMapping("/search")
    public ResponseEntity<List<Note>> buscarByTitle(@RequestParam String title) {
        List<Note> notas = noteService.searchByTitle(title);
        return ResponseEntity.ok(notas);
    }


    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody Note noteActualizada) {
        Note noteExistente = noteService.findById(id);
        if (noteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        noteExistente.setTitle(noteActualizada.getTitle());
        noteExistente.setDescription(noteActualizada.getDescription());
        noteExistente.setCompleted(noteActualizada.isCompleted());
        return ResponseEntity.ok(noteService.save(noteExistente));
    }

}
