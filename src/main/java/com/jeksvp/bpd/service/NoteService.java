package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {

    NoteResponse getNote(String diaryId, String noteId);

    NoteResponse createNote(String diaryId, CreateNoteRequest createNoteRequest);

    NoteResponse updateNote(String diaryId, String noteId, UpdateNoteRequest updateNoteRequest);

    void deleteNote(String diaryId, String noteId);

    List<NoteResponse> getNotesByDiary(String diaryId);
}
