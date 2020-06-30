package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Note {

    private final String id;
    private final Event event;
    private final EmotionalEvaluation emotionalEvaluation;
    private final BodyReaction bodyReaction;
    private final MyThoughts myThoughts;
    private final OppositeThoughts oppositeThoughts;
    private final LocalDateTime createDate;

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
