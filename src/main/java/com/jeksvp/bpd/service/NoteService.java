package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {

    NoteResponse getNote(String username, String noteId);

    NoteResponse createNote(String username, CreateNoteRequest createNoteRequest);

    NoteResponse updateNote(String username, String noteId, UpdateNoteRequest updateNoteRequest);

    void deleteNote(String username, String noteId);

    List<NoteResponse> getNotesByDiary(String username);
}
