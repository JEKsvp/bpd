package com.jeksvp.bpd.integration.helpers.kafka;

import lombok.SneakyThrows;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConsumer<K, V> {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private String lastReceivedMessage = null;

    public TestConsumer(String topic,
                        ConsumerFactory<? extends K, ? extends V> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, String>) record -> {
            this.lastReceivedMessage = record.value();
            this.countDownLatch.countDown();
        });
        KafkaMessageListenerContainer<? extends K, ? extends V> kafkaMessageListenerContainer = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        kafkaMessageListenerContainer.start();
    }

    public void assertNextMessage(String expected) {
        assertEquals(expected, lastReceivedMessage);
    }

    @SneakyThrows
    public void await(long timeout, TimeUnit unit) {
        this.countDownLatch.await(timeout, unit);
    }
}
