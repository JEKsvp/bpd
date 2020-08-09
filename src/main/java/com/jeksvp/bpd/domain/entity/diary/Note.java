package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Note {

    private String id;
    private Event event;
    private EmotionalEvaluation emotionalEvaluation;
    private BodyReaction bodyReaction;
    private MyThoughts myThoughts;
    private OppositeThoughts oppositeThoughts;
    private LocalDateTime createDate;

    public static Note create(Event event,
                              EmotionalEvaluation emotionalEvaluation,
                              BodyReaction bodyReaction,
                              MyThoughts myThoughts,
                              OppositeThoughts oppositeThoughts) {
        return Note.builder()
                .id(UUID.randomUUID().toString())
                .event(event)
                .emotionalEvaluation(emotionalEvaluation)
                .bodyReaction(bodyReaction)
                .myThoughts(myThoughts)
                .oppositeThoughts(oppositeThoughts)
                .createDate(LocalDateTime.now())
                .build();
    }
}
