package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.NoteService;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.NoteResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries/{diaryId}/notes")
public class NoteController {

    public NoteService noteService;

    @GetMapping("/{noteId}")
    public NoteResponse getNoteById(@PathVariable String diaryId,
                                    @PathVariable String noteId) {
        return noteService.getNote(diaryId, noteId);
    }

    @PostMapping
    public NoteResponse createNote(@PathVariable String diaryId,
                                   @RequestBody @Valid CreateNoteRequest createNoteRequest) {
        return noteService.createNote(diaryId, createNoteRequest);
    }

    @PutMapping("/{noteId}")
    public NoteResponse updateNote(@PathVariable String diaryId,
                                   @PathVariable String noteId,
                                   @RequestBody @Valid UpdateNoteRequest updateNoteRequest) {
        return noteService.updateNote(diaryId, noteId, updateNoteRequest);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable String diaryId,
                           @PathVariable String noteId) {
        noteService.deleteNote(diaryId, noteId);
    }

    @GetMapping
    public List<NoteResponse> getNotesByDiary(@PathVariable String diaryId){
        return noteService.getNotesByDiary(diaryId);
    }
}
