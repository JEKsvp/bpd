package com.jeksvp.bpd.web.dto.creator;

import com.jeksvp.bpd.domain.entity.diary.*;
import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.UuidSource;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.web.dto.request.note.CreateNoteRequest;
import org.springframework.stereotype.Component;

@Component
public class NoteCreator implements Creator<CreateNoteRequest, Note> {

    private final ClockSource clockSource;
    private final UuidSource uuidSource;

    public NoteCreator(ClockSource clockSource, UuidSource uuidSource) {
        this.clockSource = clockSource;
        this.uuidSource = uuidSource;
    }

    @Override
    public Note create(CreateNoteRequest request) {
        return Note.create(
                Event.create(request.getEvent().getDescription()),
                EmotionalEvaluation.create(request.getEmotionalEvaluation().getDescription()),
                BodyReaction.create(request.getBodyReaction().getDescription()),
                MyThoughts.create(request.getMyThoughts().getDescription()),
                OppositeThoughts.create(request.getOppositeThoughts().getDescription()),
                clockSource,
                uuidSource
        );
    }
}
