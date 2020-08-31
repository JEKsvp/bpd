package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.access.AccessList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessListRepository extends MongoRepository<AccessList, String> {
}
