package com.jeksvp.bpd.kafka.producer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMessage;
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
    public void sendAccessRequestMessage(@Valid AccessRequestMessage accessRequestMessage) {
        String message = objectMapper.writeValueAsString(accessRequestMessage);
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(ACCESS_REQUEST_TOPIC, accessRequestMessage.getId(), message);
        future.get();
    }
}
