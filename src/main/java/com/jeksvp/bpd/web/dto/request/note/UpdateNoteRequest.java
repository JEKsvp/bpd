package com.jeksvp.bpd.web.dto.request.note;

import com.jeksvp.bpd.web.dto.common.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteRequest {
    private EventDto event;
    private EmotionalEvaluationDto emotionalEvaluation;
    private BodyReactionDto bodyReaction;
    private MyThoughtsDto myThoughts;
    private OppositeThoughtsDto oppositeThoughts;
}
