package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.diary.*;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.DiaryRepository;
import com.jeksvp.bpd.service.NoteService;
import com.jeksvp.bpd.web.dto.creator.Creator;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.note.NoteResponse;
import com.jeksvp.bpd.web.dto.updater.Updater;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final DiaryRepository diaryRepository;
    private final Creator<CreateNoteRequest, Note> noteCreator;
    private final Updater<UpdateNoteRequest, Note> noteUpdater;

    public NoteServiceImpl(DiaryRepository diaryRepository,
                           Creator<CreateNoteRequest, Note> noteCreator,
                           Updater<UpdateNoteRequest, Note> noteUpdater) {
        this.diaryRepository = diaryRepository;
        this.noteCreator = noteCreator;
        this.noteUpdater = noteUpdater;
    }

    @Override
    public NoteResponse getNote(String username, String noteId) {
        Diary diary = getDiary(username);
        Note note = getNoteByDiary(diary, noteId);
        return NoteResponse.create(note);
    }

    @Override
    public NoteResponse createNote(String username, CreateNoteRequest request) {
        Note note = noteCreator.create(request);
        Diary diary = getDiary(username);
        diary.addNote(note);
        diaryRepository.save(diary);
        return NoteResponse.create(note);
    }

    @Override
    public NoteResponse updateNote(String username, String noteId, UpdateNoteRequest request) {
        Diary diary = getDiary(username);
        Note note = getNoteByDiary(diary, noteId);
        noteUpdater.update(request, note);
        diaryRepository.save(diary);
        return NoteResponse.create(note);
    }

    @Override
    public void deleteNote(String username, String noteId) {
        Diary diary = getDiary(username);
        if (diary.getNotes().stream().anyMatch(note -> noteId.equals(note.getId()))) {
            diary.removeNote(noteId);
            diaryRepository.save(diary);
        } else {
            throw new ApiException(ApiErrorContainer.NOTE_NOT_FOUND);
        }
    }

    @Override
    public List<NoteResponse> getNotesByDiary(String username) {
        return getDiary(username)
                .getNotes().stream()
                .sorted(Comparator.comparing(Note::getCreateDate).reversed())
                .map(NoteResponse::create)
                .collect(Collectors.toList());
    }

    private Note getNoteByDiary(Diary diary, String noteId) {
        return diary
                .getNotes().stream()
                .filter(note -> noteId.equals(note.getId()))
                .findFirst()
                .orElseThrow(() -> new ApiException(ApiErrorContainer.NOTE_NOT_FOUND));
    }

    private Diary getDiary(String username) {
        return diaryRepository.findById(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.DIARY_NOT_FOUND));
    }
}
