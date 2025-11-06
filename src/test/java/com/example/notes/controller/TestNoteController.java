package com.example.notes.controller;

import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional //lo guarda en BBDD y luego hace rollback y lo borra.
@SpringBootTest //carga la app por completo y simula un servidor pero no levanta el real.
@AutoConfigureMockMvc //simulador de peticiones http."levanta" servlets y http
public class TestNoteController {

    @Autowired
    private MockMvc mockMvc; //HTTP y Servlets
    @Autowired
    private NoteRepository noteRepository;

    @Test
    void shouldCreateNewNoteApi() throws Exception {
        //Arrange
        String newNoteJson = "{\"title\": \"Note title\",\"description\": \"Noteeeeeee2 test\", \"completed\":false}";

        //Act and Assert

        mockMvc.perform(post("/api/notas")
                        .with(user("testUser").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newNoteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Noteeeeeee2 test"))
                .andExpect(jsonPath("$.id").exists());


    }

    @Test
    void shouldGetAllNotes() throws Exception {

        mockMvc.perform(get("/api/notas")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());

    }

//    @Test
//    void shouldDeleteNote() throws Exception {
//        Note nota = new Note("Nota a borrar", "Descripción", false);
//        nota = noteRepository.save(nota);
//
//        mockMvc.perform(delete("/api/notas/{id}", nota.getId())
//                        .with(user("test").roles("USER"))
//                        .with(csrf()))
//                .andExpect(status().isNoContent());
//
//        boolean existe = noteRepository.existsById(nota.getId());
//        assertFalse(existe);
//    }


}
