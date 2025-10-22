package com.example.notes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional //lo guarda en BBDD y luego hace rollback y lo borra.
@SpringBootTest //carga la app por completo y simula un servidor pero no levanta el real.
@AutoConfigureMockMvc //simulador de peticiones http."levanta" servlets y http
public class TestNoteController {

    @Autowired
    private MockMvc mockMvc; //HTTP y Servlets

    @Test
    void debeCrearUnaNuevaNotePorApi() throws Exception {
        //Arrange
        String newNoteJson = "{\"title\": \"Note title\",\"description\": \"Noteeeeeee2 test\", \"completed\":false}";

        //Act and Assert

        mockMvc.perform(post("/api/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newNoteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Noteeeeeee2 test"))
                .andExpect(jsonPath("$.id").exists());


    }

}
