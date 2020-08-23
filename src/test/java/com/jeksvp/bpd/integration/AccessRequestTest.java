package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.configuration.KafkaTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.integration.helpers.TestTime;
import com.jeksvp.bpd.integration.helpers.TestUserCreator;
import com.jeksvp.bpd.integration.helpers.TokenObtainer;
import com.jeksvp.bpd.integration.helpers.kafka.TestConsumer;
import com.jeksvp.bpd.kafka.Topics;
import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.UuidSource;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationTestConfiguration.class, KafkaTestConfiguration.class})
@ActiveProfiles("test")
@EmbeddedKafka
public class AccessRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    private TestConsumer<String, String> testConsumer;

    @Autowired
    private TokenObtainer tokenObtainer;

    @MockBean
    private UuidSource uuidSource;

    @MockBean
    private ClockSource clockSource;

    private HttpHeaders defaultAuthHeader;

    @BeforeEach
    public void init() {
        this.defaultAuthHeader = tokenObtainer.obtainDefaultClientHeader(mockMvc);
        testConsumer = new TestConsumer<>(Topics.ACCESS_REQUEST_TOPIC, consumerFactory);
    }

    @Test
    public void sendAccessToNonExistsUser() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-to-non-exists-user-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-to-non-exists-user-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }

    @Test
    public void clientCantSendRequestToAnotherClient() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-client-to-client-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-client-to-client-response.json"), Charset.defaultCharset());

        String user = "ClientToClientAccReq";
        TestUserCreator.createUser(mockMvc, user, Role.CLIENT);
        HttpHeaders headers = tokenObtainer.obtainAuthHeader(mockMvc, user, TestUserCreator.PASSWORD);

        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }

    @Test
    public void therapistCantSendRequestToAnotherTherapist() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-therapist-to-therapist-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-therapist-to-therapist-response.json"), Charset.defaultCharset());

        String user = "TherToTherAccReq";
        TestUserCreator.createUser(mockMvc, user, Role.THERAPIST);
        HttpHeaders headers = tokenObtainer.obtainAuthHeader(mockMvc, user, TestUserCreator.PASSWORD);

        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }

    @Test
    public void therapistCantSendRequestToClient() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-therapist-to-client-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-from-therapist-to-client-response.json"), Charset.defaultCharset());

        String user = "TherToClientAccReq";
        TestUserCreator.createUser(mockMvc, user, Role.THERAPIST);
        HttpHeaders headers = tokenObtainer.obtainAuthHeader(mockMvc, user, TestUserCreator.PASSWORD);

        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sendAccessRequestToTherapist() throws Exception {
        when(uuidSource.random())
                .thenReturn(UUID.fromString("ba2a9999-3023-4527-b376-4f0a58de7e5d"));
        when(clockSource.getClock())
                .thenReturn(TestTime.DEFAULT_CLOCK);

        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-to-therapist-request.json"), Charset.defaultCharset());
        String kafkaMessage = IOUtils.toString(getClass().getResource("/kafka/access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(200));

        testConsumer.await(5, TimeUnit.SECONDS);
        testConsumer.assertNextMessage(kafkaMessage);
    }
}
