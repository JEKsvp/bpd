package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TherapistAccessRepository extends MongoRepository<TherapistAccessList, String> {
}
