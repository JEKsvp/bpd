package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.integration.helpers.TokenObtainer;
import com.jeksvp.bpd.integration.helpers.TestUserCreator;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class TherapistIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenObtainer tokenObtainer;

    @Test
    public void emptyTherapistAccessesForTherapistAfterSignUp() throws Exception {
        String username = "emptyClientsTherapistSignUp";
        TestUserCreator.createUser(mockMvc, username, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, TestUserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/current/therapist-accesses")
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[]"));
    }

    @Test
    public void notFoundTherapistAccessesForClientAfterSignUp() throws Exception {
        String username = "404ClientsClientSignUp";
        TestUserCreator.createUser(mockMvc, username, Role.CLIENT);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, TestUserCreator.PASSWORD);

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/therapist-controller/tharapist-accesses-not-found-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                get("/api/v1/users/current/therapist-accesses")
                        .headers(authHeader))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPageOfTherapistsTest() throws Exception {
        String username1 = "getPageOfTherapistsTest1";
        String username2 = "getPageOfTherapistsTest2";

        TestUserCreator.createUser(mockMvc, username1, Role.THERAPIST);
        TestUserCreator.createUser(mockMvc, username2, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainDefaultClientHeader(mockMvc);

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/therapist-controller/therapists-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                get("/api/v1/therapists")
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(responseBody));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPageOfTherapistsByQueryTest() throws Exception {
        String username1 = "getPageOfTherapistsTest1";
        String username2 = "getPageOfTherapistsTest2";

        TestUserCreator.createUser(mockMvc, username1, Role.THERAPIST);
        TestUserCreator.createUser(mockMvc, username2, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainDefaultClientHeader(mockMvc);

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/therapist-controller/therapists-by-query-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                get("/api/v1/therapists")
                        .headers(authHeader)
                        .queryParam("query", "getPageOfTherapists"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(responseBody));
    }
}
