package com.jeksvp.bpd.web.dto.request.note;

import com.jeksvp.bpd.web.dto.common.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateNoteRequest {

    private EventDto event;
    private EmotionalEvaluationDto emotionalEvaluation;
    private BodyReactionDto bodyReaction;
    private MyThoughtsDto myThoughts;
    private OppositeThoughtsDto oppositeThoughts;
}
