package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.NoteService;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.response.NoteResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{username}/diary/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{noteId}")
    public NoteResponse getNoteById(@PathVariable String username,
                                    @PathVariable String noteId) {
        return noteService.getNote(username, noteId);
    }

    @PostMapping
    public NoteResponse createNote(@PathVariable String username,
                                   @RequestBody @Valid CreateNoteRequest createNoteRequest) {
        return noteService.createNote(username, createNoteRequest);
    }

    @PutMapping("/{noteId}")
    public NoteResponse updateNote(@PathVariable String username,
                                   @PathVariable String noteId,
                                   @RequestBody @Valid UpdateNoteRequest updateNoteRequest) {
        return noteService.updateNote(username, noteId, updateNoteRequest);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable String username,
                           @PathVariable String noteId) {
        noteService.deleteNote(username, noteId);
    }

    @GetMapping
    public List<NoteResponse> getNotesByDiary(@PathVariable String username) {
        return noteService.getNotesByDiary(username);
    }
}
