package com.example.notes.repository.service.service;

import com.example.notes.exception.NoteNotFoundException;
import com.example.notes.exception.NoteNotSaveException;
import com.example.notes.exception.NotesEmptyException;
import com.example.notes.model.AppUser;
import com.example.notes.model.Note;
import com.example.notes.repository.service.AppUserRepository;
import com.example.notes.repository.service.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final AppUserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, AppUserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;

    }

//    // Guardar nota
//    public Note save(Note note) {
//        if (note == null) {
//            throw new NoteNotSaveException("Note not saved");
//        }
//        return noteRepository.save(note);
//    }


    //  Crear una nueva nota para un usuario
    public Note saveForUser(Note note, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        note.setUser(user);
        return noteRepository.save(note);
    }

//    // Obtener todas las notas
//    public List<Note> findAll() {
//        List<Note> notes = noteRepository.findAll();
//        if (notes.isEmpty()) {
//            throw new NotesEmptyException("No hay notas disponibles");
//        }
//        return notes;
//    }


    //mostrar todas las notas del usuario.
    public List<Note> findAllByUser(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return noteRepository.findByUser(user);
    }


    // Obtener nota por id
    public Note findByIdForUser(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Acceso denegado: la nota no pertenece al usuario actual");
        }

        return note;
    }


    // Buscar notas por título
    public List<Note> searchByTitle(Long userId,String title) {
        if (title == null || title.isEmpty()) {
            throw new NoteNotSaveException("Título no puede estar vacío");
        }
        return noteRepository.findByUserIdAndTitleContainingIgnoreCase(userId,title);
    }

    // Actualizar nota
    public Note updateNote(Long id, Note noteActualizada, String username) {

        Note noteExistente = findByIdForUser(id, username);

        noteExistente.setTitle(noteActualizada.getTitle());
        noteExistente.setDescription(noteActualizada.getDescription());
        noteExistente.setCompleted(noteActualizada.isCompleted());
        return noteRepository.save(noteExistente);
    }

    // Borrar nota
    public void deleteById(Long id, String username) {
        Note note = findByIdForUser(id, username);
        noteRepository.delete(note);
    }
}
