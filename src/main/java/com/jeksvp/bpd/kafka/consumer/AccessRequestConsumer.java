package com.jeksvp.bpd.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeksvp.bpd.kafka.GroupIds;
import com.jeksvp.bpd.kafka.Topics;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.service.AccessService;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccessRequestConsumer {

    private final AccessService accessService;
    private final ObjectMapper objectMapper;

    public AccessRequestConsumer(AccessService accessService, ObjectMapper objectMapper) {
        this.accessService = accessService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(groupId = GroupIds.ACCESS_REQUEST_CONSUMER_GROUP, topics = Topics.ACCESS_REQUEST_TOPIC)
    @SneakyThrows
    public void consumeAccessRequest(String accessRequest) {
        AccessRequestMsg accessRequestMsg = objectMapper.readValue(accessRequest, AccessRequestMsg.class);
        accessService.updateAccessStatus(accessRequestMsg);
    }
}
