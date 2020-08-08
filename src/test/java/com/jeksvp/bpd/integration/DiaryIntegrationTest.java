package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class DiaryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenObtainer tokenObtainer;

    @Test
    public void patientMustHasDiary() throws Exception {
        String username = "testPatient";
        UserCreator.createUser(mockMvc, username, Role.PATIENT);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, UserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", username)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.notes").isArray());
    }

    @Test
    public void psychotherapistMustNotHasDiary() throws Exception {
        String username = "testPsychotherapist";
        UserCreator.createUser(mockMvc, username, Role.PSYCHOTHERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, UserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", username)
                        .headers(authHeader))
                .andExpect(status().is(404));
    }
}
