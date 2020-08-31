package com.jeksvp.bpd.integration.helpers.kafka;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class KafkaListenerSynchronizationAspect {

    private final KafkaListenerAwaiter synchronizer;

    public KafkaListenerSynchronizationAspect(KafkaListenerAwaiter synchronizer) {
        this.synchronizer = synchronizer;
    }


    @After("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    public void logExecutionTime() {
        synchronizer.countDown();
    }

}
