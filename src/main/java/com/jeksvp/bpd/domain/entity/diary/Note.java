package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Note {

    private String id;
    private Event event;
    private EmotionalEvaluation emotionalEvaluation;
    private BodyReaction bodyReaction;
    private MyThoughts myThoughts;
    private OppositeThoughts oppositeThoughts;

    public static Note create(Note note) {
        return Note.builder()
                .id(UUID.randomUUID().toString())
                .event(Event.create(note.getEvent()))
                .emotionalEvaluation(EmotionalEvaluation.create(note.getEmotionalEvaluation()))
                .bodyReaction(BodyReaction.create(note.getBodyReaction()))
                .myThoughts(MyThoughts.create(note.getMyThoughts()))
                .oppositeThoughts(OppositeThoughts.create(note.getOppositeThoughts()))
                .build();
    }

    public Note update(Note note) {
        this.event.update(note.getEvent());
        this.emotionalEvaluation.update(note.getEmotionalEvaluation());
        this.bodyReaction.update(note.getBodyReaction());
        this.myThoughts.update(note.getMyThoughts());
        this.oppositeThoughts.update(note.getOppositeThoughts());
        return this;
    }
}
