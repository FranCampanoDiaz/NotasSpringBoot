package com.example.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja la excepción cuando una nota no se encuentra
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoteNotFound(NoteNotFoundException ex) {
        // Código 404 (Not Found)
        HttpStatus status = HttpStatus.NOT_FOUND;

        // 1. Crea el objeto ErrorResponse
        ErrorResponse error = new ErrorResponse(status.value(), ex.getMessage());

        // 2. Devuelve ResponseEntity, que convierte 'error' a JSON y establece el 404
        return new ResponseEntity<>(error, status);
    }
}
