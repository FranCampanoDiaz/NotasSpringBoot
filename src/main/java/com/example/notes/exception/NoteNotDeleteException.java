package com.example.notes.exception;

public class NoteNotDeleteException extends RuntimeException {
    public NoteNotDeleteException() {
        super("No se ha podido eliminar la nota.");
    }
}
