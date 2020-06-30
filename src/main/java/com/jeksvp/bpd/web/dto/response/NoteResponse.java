package com.jeksvp.bpd.web.dto.response;

import com.jeksvp.bpd.domain.entity.diary.Note;
import com.jeksvp.bpd.web.dto.common.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private String id;
    private EventDto event;
    private EmotionalEvaluationDto emotionalEvaluation;
    private BodyReactionDto bodyReaction;
    private MyThoughtsDto myThoughts;
    private OppositeThoughtsDto oppositeThoughts;

    public static NoteResponse create(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .event(EventDto.create(note.getEvent()))
                .emotionalEvaluation(EmotionalEvaluationDto.create(note.getEmotionalEvaluation()))
                .bodyReaction(BodyReactionDto.create(note.getBodyReaction()))
                .myThoughts(MyThoughtsDto.create(note.getMyThoughts()))
                .oppositeThoughts(OppositeThoughtsDto.create(note.getOppositeThoughts()))
                .build();
    }
}
