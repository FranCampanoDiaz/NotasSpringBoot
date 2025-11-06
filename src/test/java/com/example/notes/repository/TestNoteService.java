package com.example.notes.repository;


import com.example.notes.model.AppUser;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private AppUserRepository appuserRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void testFindAllByUser() {
        // Arrange
        // Crear usuario simulado
        String name = "fran";
        AppUser user = new AppUser();
        user.setUsername(name);
        user.setPassword("password");

        // Creo notas simuladas para ese usuario
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("note 1");
        note1.setDescription("note 1 description");
        note1.setCompleted(false);
        note1.setUser(user);

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("note 2");
        note2.setDescription("note 2 description");
        note2.setCompleted(false);
        note2.setUser(user);

        List<Note> listaSimulada = Arrays.asList(note1, note2);

        // Simular la respuesta de userRepository y noteRepository
        when(appuserRepository.findByUsername(name)).thenReturn(Optional.of(user));  // Simular el usuario
        when(noteRepository.findByUser(user)).thenReturn(listaSimulada);  // Simular las notas del usuario

        // Act
        List<Note> notes = noteService.findAllByUser(name);

        // Assert
        assertEquals(2, notes.size());
        assertEquals("note 1 description", notes.get(0).getDescription());
        assertEquals("note 2 description", notes.get(1).getDescription());
        assertEquals(name, notes.get(0).getUser().getUsername());  // Verificar que las notas son del usuario correcto
        assertEquals(name, notes.get(1).getUser().getUsername());
    }


    @Test
    public void testDeleteNoteById() {
        // Arrange
        String username = "fran";
        // Crear una nota simulada asociada al usuario
        AppUser user = new AppUser();
        user.setUsername(username);
        Note note = new Note("note 1", "note 1 description", false, user);
        Long noteId = note.getId();


        // Simular que findByIdForUser encuentra la nota del usuario
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));  // Simulamos que la nota existe
        doNothing().when(noteRepository).delete(note);  // Simulamos que no ocurre nada cuando se llama a delete

        // Act
        noteService.deleteById(noteId, username);

        // Assert
        verify(noteRepository, times(1)).delete(note);  // Verificar que el repositorio llamó a delete
    }


    @Test
    public void testSaveNote() {
        Note note = new Note();
        note.setTitle("note 1");
        note.setDescription("note 1 description");
        note.setCompleted(false);
        AppUser user = new AppUser();
        String username = "fran";
        user.setUsername(username);
        when(appuserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(noteRepository.save(note)).thenReturn(note);
        Note noteSaved = noteService.saveForUser(note, username);
        assertEquals(note, noteSaved);
        assertEquals(user, noteSaved.getUser());
    }


    @Test
    public void testSearchByTitle() {
        // Arrange
        String username = "fran";
        AppUser user = new AppUser();
        user.setUsername(username);

        Note note = new Note();
        note.setTitle("note 1");


        // Simulamos que el repositorio de notas encuentra la nota por título
        when(noteRepository.findByUserIdAndTitleContainingIgnoreCase(user.getId(), "note 1"))
                .thenReturn(Arrays.asList(note));

        // Act
        List<Note> notesFound = noteService.searchByTitle(user.getId(), "note 1");

        // Assert
        assertEquals(1, notesFound.size());
        assertEquals(note, notesFound.get(0));
    }

}
