package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.configuration.KafkaTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.access.Access;
import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.integration.helpers.TestTime;
import com.jeksvp.bpd.integration.helpers.TestUserCreator;
import com.jeksvp.bpd.integration.helpers.TokenObtainer;
import com.jeksvp.bpd.integration.helpers.kafka.KafkaListenerAwaiter;
import com.jeksvp.bpd.integration.helpers.kafka.TestConsumer;
import com.jeksvp.bpd.integration.models.DefaultUser;
import com.jeksvp.bpd.kafka.Topics;
import com.jeksvp.bpd.repository.AccessListRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Autowired
    private AccessListRepository accessListRepository;

    @Autowired
    private KafkaListenerAwaiter kafkaListenerAwaiter;

    @MockBean
    private UuidSource uuidSource;

    @MockBean
    private ClockSource clockSource;

    private HttpHeaders defaultClientHeader;
    private HttpHeaders defaultTherapistHeader;

    @BeforeEach
    public void init() {
        this.defaultClientHeader = tokenObtainer.obtainDefaultClientHeader(mockMvc);
        this.defaultTherapistHeader = tokenObtainer.obtainDefaultTherapistHeader(mockMvc);
        testConsumer = new TestConsumer<>(Topics.ACCESS_REQUEST_TOPIC, consumerFactory);
    }

    @Test
    public void sendAccessToNonExistsUser() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-to-non-exists-user-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-to-non-exists-user-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
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
    public void therapistCantSendPendingRequestToClient() throws Exception {
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
                .andExpect(status().is(409))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void cantSendAcceptAccessRequestWithoutPending() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-accept-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/accept-without-pending-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultTherapistHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(409))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void cantSendDeclineAccessRequestWithoutPending() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-decline-from-therapist-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/accept-without-pending-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultTherapistHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(409))
                .andExpect(content().json(responseBody));
    }

    @Test
    public void clientCantSendAcceptRequestToTherapist() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-accept-access-from-user-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/client-cant-accept-response.json"), Charset.defaultCharset());

        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(409))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sendPendingAndAcceptAccessRequests() throws Exception {
        when(uuidSource.random())
                .thenReturn(UUID.fromString("ba2a9999-3023-4527-b376-4f0a58de7e5d"));
        when(clockSource.getClock())
                .thenReturn(TestTime.DEFAULT_CLOCK);

        String pendingRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-pending-request.json"), Charset.defaultCharset());
        String pendingKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/pending-access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pendingRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(pendingKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(clientAccess -> findExpectedTherapistWithStatus(clientAccess, AccessStatus.PENDING))
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(therapistAccess -> findExpectedClientWithStatus(therapistAccess, AccessStatus.PENDING))
        );

        String acceptRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-accept-request.json"), Charset.defaultCharset());
        String acceptKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/accept-access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultTherapistHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(acceptKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(clientAccess -> findExpectedTherapistWithStatus(clientAccess, AccessStatus.ACCEPT))
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(therapistAccess -> findExpectedClientWithStatus(therapistAccess, AccessStatus.ACCEPT))
        );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void clientCantSendPendingOrAccessRequestWithExistsPendingRequestAccessRequests() throws Exception {
        when(uuidSource.random())
                .thenReturn(UUID.fromString("ba2a9999-3023-4527-b376-4f0a58de7e5d"));
        when(clockSource.getClock())
                .thenReturn(TestTime.DEFAULT_CLOCK);

        String pendingRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-pending-request.json"), Charset.defaultCharset());
        String pendingKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/pending-access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pendingRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(pendingKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(clientAccess -> findExpectedTherapistWithStatus(clientAccess, AccessStatus.PENDING))
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(therapistAccess -> findExpectedClientWithStatus(therapistAccess, AccessStatus.PENDING))
        );

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/client-already-has-therapist-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pendingRequestBody))
                .andExpect(status().is(409))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sendPendingAndDeclineFromTherapistAccessRequests() throws Exception {
        when(uuidSource.random())
                .thenReturn(UUID.fromString("ba2a9999-3023-4527-b376-4f0a58de7e5d"));
        when(clockSource.getClock())
                .thenReturn(TestTime.DEFAULT_CLOCK);

        String pendingRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-pending-request.json"), Charset.defaultCharset());
        String pendingKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/pending-access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pendingRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(pendingKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(clientAccess -> findExpectedTherapistWithStatus(clientAccess, AccessStatus.PENDING))
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(therapistAccess -> findExpectedClientWithStatus(therapistAccess, AccessStatus.PENDING))
        );

        String acceptRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-decline-from-therapist-request.json"), Charset.defaultCharset());
        String declineKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/decline-access-request-from-therapist-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultTherapistHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(declineKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().isEmpty()
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().isEmpty()
        );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sendPendingAndDeclineFromClientAccessRequests() throws Exception {
        when(uuidSource.random())
                .thenReturn(UUID.fromString("ba2a9999-3023-4527-b376-4f0a58de7e5d"));
        when(clockSource.getClock())
                .thenReturn(TestTime.DEFAULT_CLOCK);

        String pendingRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-pending-request.json"), Charset.defaultCharset());
        String pendingKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/pending-access-request-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pendingRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(pendingKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(clientAccess -> findExpectedTherapistWithStatus(clientAccess, AccessStatus.PENDING))
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().stream()
                .anyMatch(therapistAccess -> findExpectedClientWithStatus(therapistAccess, AccessStatus.PENDING))
        );

        String acceptRequestBody = IOUtils.toString(getClass().getResource("/web/controller/access-controller/send-valid-decline-from-client-request.json"), Charset.defaultCharset());
        String declineKafkaMessage = IOUtils.toString(getClass().getResource("/kafka/decline-access-request-from-client-message.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/access-request")
                        .headers(defaultClientHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody))
                .andExpect(status().is(200));

        testConsumer.assertNextMessage(declineKafkaMessage, 5);
        kafkaListenerAwaiter.await(5);

        assertTrue(accessListRepository.findById(DefaultUser.JEKSVP_USERNAME)
                .orElseThrow().getAccesses().isEmpty()
        );

        assertTrue(accessListRepository.findById(DefaultUser.PSYCHO_USERNAME)
                .orElseThrow().getAccesses().isEmpty()
        );
    }

    private boolean findExpectedTherapistWithStatus(Access access, AccessStatus status) {
        return DefaultUser.PSYCHO_USERNAME.equals(access.getUsername())
                && status.equals(access.getStatus());
    }

    private boolean findExpectedClientWithStatus(Access access, AccessStatus status) {
        return DefaultUser.JEKSVP_USERNAME.equals(access.getUsername())
                && status.equals(access.getStatus());
    }
}
