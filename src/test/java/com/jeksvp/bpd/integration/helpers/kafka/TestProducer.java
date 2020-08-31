package com.jeksvp.bpd.integration.helpers.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class TestProducer<T> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestProducer(KafkaTemplate<String, String> kafkaTemplate,
                        String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @SneakyThrows
    public void send(T message) {
        String messageStr = objectMapper.writeValueAsString(message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, messageStr);
        future.get();
    }

    @SneakyThrows
    public void send(String key, T message) {
        String messageStr = objectMapper.writeValueAsString(message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, messageStr);
        future.get();
    }
}
