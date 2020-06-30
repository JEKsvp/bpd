package com.jeksvp.bpd.web.dto.updater.impl;

import com.jeksvp.bpd.domain.entity.diary.Note;
import com.jeksvp.bpd.web.dto.request.note.UpdateNoteRequest;
import com.jeksvp.bpd.web.dto.updater.Updater;
import org.springframework.stereotype.Component;

@Component
public class NoteUpdater implements Updater<UpdateNoteRequest, Note> {
    @Override
    public void update(UpdateNoteRequest request, Note note) {
        note.getEvent().update(request.getEvent().getDescription());
        note.getBodyReaction().update(request.getBodyReaction().getDescription());
        note.getEmotionalEvaluation().update(request.getEmotionalEvaluation().getDescription());
        note.getMyThoughts().update(request.getMyThoughts().getDescription());
        note.getOppositeThoughts().update(request.getOppositeThoughts().getDescription());
    }
}
