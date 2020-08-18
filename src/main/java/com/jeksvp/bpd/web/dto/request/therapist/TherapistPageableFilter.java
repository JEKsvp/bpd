package com.jeksvp.bpd.web.dto.request.therapist;

import com.jeksvp.bpd.domain.entity.QUser;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.web.dto.request.PageableFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TherapistPageableFilter extends PageableFilter {
    private String query;

    public Predicate getMongoPredicate() {
        BooleanExpression predicate = QUser.user.roles.contains(Role.THERAPIST);
        if (this.getQuery() != null) {
            predicate = predicate.and(QUser.user.username.containsIgnoreCase(this.getQuery()))
                    .or(QUser.user.firstName.containsIgnoreCase(this.getQuery()))
                    .or(QUser.user.lastName.containsIgnoreCase(this.getQuery()));
        }
        return predicate;
    }
}



