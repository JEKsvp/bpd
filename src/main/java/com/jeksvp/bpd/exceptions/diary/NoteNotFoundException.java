package com.jeksvp.bpd.exceptions.diary;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException() {
        super();
    }

    public NoteNotFoundException(String message) {
        super(message);
    }
}
