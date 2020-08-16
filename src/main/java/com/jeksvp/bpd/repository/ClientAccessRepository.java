package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientAccessRepository extends MongoRepository<ClientAccessList, String> {

}
