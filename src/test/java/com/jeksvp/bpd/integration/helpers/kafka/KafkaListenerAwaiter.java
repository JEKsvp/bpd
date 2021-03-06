package com.jeksvp.bpd.integration.helpers.kafka;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Provides synchronization for tests with {@link org.springframework.kafka.annotation.KafkaListener}
 */
public class KafkaListenerAwaiter {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    void countDown() {
        countDownLatch.countDown();
    }

    @SneakyThrows
    public void await(long timeoutInSeconds) {
        try {
            boolean isReachedToZero = countDownLatch.await(timeoutInSeconds, TimeUnit.SECONDS);
            assertTrue(isReachedToZero, "There was no message consume by KafkaListener.");
        } finally {
            reset();
        }
    }


    private void reset() {
        this.countDownLatch = new CountDownLatch(1);
    }
}
