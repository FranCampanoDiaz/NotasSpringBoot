package com.example.notes.exception;

public class NotesEmptyException extends RuntimeException {
    public NotesEmptyException(String message) {
        super(message);
    }
}
