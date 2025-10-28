package com.example.notes.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {

        super("Tarea Not Found with id: "+ id);

    }
}
