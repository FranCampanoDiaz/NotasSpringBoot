package com.example.notes.repository.service.service;

import com.example.notes.exception.NoteNotFoundException;
import com.example.notes.exception.NoteNotSaveException;
import com.example.notes.exception.NotesEmptyException;
import com.example.notes.model.Note;
import com.example.notes.repository.service.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Guardar nota
    public Note save(Note note) {
        if (note == null) {
            throw new NoteNotSaveException("Note not saved");
        }
        return noteRepository.save(note);
    }

    // Obtener todas las notas
    public List<Note> findAll() {
        List<Note> notes = noteRepository.findAll();
        if (notes.isEmpty()) {
            throw new NotesEmptyException("No hay notas disponibles");
        }
        return notes;
    }

    // Obtener nota por id
    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    // Buscar notas por título
    public List<Note> searchByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new NoteNotSaveException("Título no puede estar vacío");
        }
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    // Actualizar nota
    public Note updateNote(Long id, Note noteActualizada) {
        Note noteExistente = findById(id);
        noteExistente.setTitle(noteActualizada.getTitle());
        noteExistente.setDescription(noteActualizada.getDescription());
        noteExistente.setCompleted(noteActualizada.isCompleted());
        return save(noteExistente);
    }

    // Borrar nota
    public void deleteById(Long id) {
        Note note = findById(id);
        noteRepository.delete(note);
    }
}
