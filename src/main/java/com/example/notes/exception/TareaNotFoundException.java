package com.example.notes.exception;

public class TareaNotFoundException extends RuntimeException {

    public TareaNotFoundException(Long id) {

        super("Tarea Not Found with id: "+ id);

    }
}
