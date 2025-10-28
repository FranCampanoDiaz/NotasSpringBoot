package com.example.notes.exception;

public class NoteNotSaveException extends RuntimeException {
    public NoteNotSaveException(String message) {
        super(message);
    }
}
