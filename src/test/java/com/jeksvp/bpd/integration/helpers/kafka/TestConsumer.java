package com.jeksvp.bpd.integration.helpers.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestConsumer<K, V> {

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private String lastReceivedMessage = null;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestConsumer(String topic,
                        ConsumerFactory<? extends K, ? extends V> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, String>) this::consumeMessage);
        KafkaMessageListenerContainer<? extends K, ? extends V> kafkaMessageListenerContainer = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        kafkaMessageListenerContainer.start();
    }

    private void consumeMessage(ConsumerRecord<String, String> record) {
        this.lastReceivedMessage = record.value();
        this.countDownLatch.countDown();
    }

    @SneakyThrows
    public void assertNextMessage(String expected, long timeoutInSeconds) {
        try {
            this.countDownLatch.await(timeoutInSeconds, TimeUnit.SECONDS);
            assertNotNull(lastReceivedMessage, "There is no message from kafka");
            JsonNode expectedJson = objectMapper.readTree(expected);
            JsonNode actualJson = objectMapper.readTree(lastReceivedMessage);
            assertEquals(expectedJson, actualJson);
        } finally {
            reset();
        }
    }

    private void reset() {
        this.countDownLatch = new CountDownLatch(1);
    }
}
