package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.access.patient.PatientAccessList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientAccessRepository extends MongoRepository<PatientAccessList, String> {
}
