package com.example.notes.repository.service;


import com.example.notes.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @Test
    public void testFindAll() {
        //Arrange (preparar)
        Note note = new Note();
        note.setId(1L);
        note.setTitle("note 1");
        note.setDescription("note 1 description");
        note.setCompleted(false);

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("note 2");
        note2.setDescription("note 2 description");
        note2.setCompleted(false);

        List<Note> listaSimulada = Arrays.asList(note, note2);

        when(noteRepository.findAll()).thenReturn(listaSimulada);

        //Act (actuar)
        List<Note> notes = noteService.findAll();

        //Assert (verificar-comparar resultados)
        assertEquals(2, notes.size());
        assertEquals("note 1 description", notes.get(0).getDescription());
        assertEquals("note 2 description", notes.get(1).getDescription());
    }

    @Test
    public void testDeleteNoteById() {
        doNothing().when(noteRepository).deleteById(1L);

        noteService.deleteById(1L);

        verify(noteRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testSaveNote() {
        Note note = new Note();
        note.setTitle("note 1");
        note.setDescription("note 1 description");
        note.setCompleted(false);

        when(noteRepository.save(note)).thenReturn(note);
        Note noteSaved = noteService.save(note);
        assertEquals(note, noteSaved);

    }

    @Test
    public void testFindById() {
        Note note = new Note();
        note.setId(1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Note noteSaved = noteService.findById(1L);
        assertEquals(note, noteSaved);

    }

    @DisplayName("Debe devolver null si no encuentra nota")
    @Test
    public void testFindByIdNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        Note result = noteService.findById(1L);
        assertNull(result);
    }
}
