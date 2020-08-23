package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.configuration.KafkaTestConfiguration;
import com.jeksvp.bpd.integration.helpers.kafka.TestConsumer;
import com.jeksvp.bpd.kafka.Topics;
import kafka.security.auth.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationTestConfiguration.class, KafkaTestConfiguration.class})
@ActiveProfiles("test")
@EmbeddedKafka
public class AccessRequestTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    private TestConsumer<String, String> testConsumer;


    @BeforeEach
    public void init() {
        testConsumer = new TestConsumer<>(Topics.ACCESS_REQUEST_TOPIC, consumerFactory);
    }

    @Test
    public void test() {
        System.out.println("test");
    }
}
