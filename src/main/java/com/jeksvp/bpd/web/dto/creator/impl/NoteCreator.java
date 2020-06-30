package com.jeksvp.bpd.web.dto.creator.impl;

import com.jeksvp.bpd.domain.entity.diary.*;
import com.jeksvp.bpd.web.dto.creator.Creator;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import org.springframework.stereotype.Component;

@Component
public class NoteCreator implements Creator<CreateNoteRequest, Note> {
    @Override
    public Note create(CreateNoteRequest request) {
        return Note.create(
                Event.create(request.getEvent().getDescription()),
                EmotionalEvaluation.create(request.getEmotionalEvaluation().getDescription()),
                BodyReaction.create(request.getBodyReaction().getDescription()),
                MyThoughts.create(request.getMyThoughts().getDescription()),
                OppositeThoughts.create(request.getOppositeThoughts().getDescription())
        );
    }
}
