package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.service.NoteService;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.NoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Override
    public NoteResponse getNote(String diaryId, String noteId) {
        return null;
    }

    @Override
    public NoteResponse createNote(String diaryId, CreateNoteRequest createNoteRequest) {
        return null;
    }

    @Override
    public NoteResponse updateNote(String diaryId, String noteId, UpdateNoteRequest updateNoteRequest) {
        return null;
    }

    @Override
    public void deleteNote(String diaryId, String noteId) {

    }

    @Override
    public List<NoteResponse> getNotesByDiary(String diaryId) {
        return null;
    }
}
