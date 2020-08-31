package com.jeksvp.bpd.kafka.producer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.validation.Valid;

import static com.jeksvp.bpd.kafka.Topics.ACCESS_REQUEST_TOPIC;

@Component
public class AccessRequestProducerImpl implements AccessRequestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AccessRequestProducerImpl(KafkaTemplate<String, String> kafkaTemplate,
                                     ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void sendAccessRequestMessage(@Valid AccessRequestMsg accessRequestMsg) {
        String message = objectMapper.writeValueAsString(accessRequestMsg);
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(ACCESS_REQUEST_TOPIC, accessRequestMsg.getId(), message);
        future.get();
    }
}
