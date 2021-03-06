package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.integration.helpers.TokenObtainer;
import com.jeksvp.bpd.integration.helpers.TestUserCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.jeksvp.bpd.integration.models.DefaultUser.ACCESSED_CLIENT_USERNAME;
import static com.jeksvp.bpd.integration.models.DefaultUser.JEKSVP_USERNAME;
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
    public void clientMustHasDiary() throws Exception {
        String username = "testClient";
        TestUserCreator.createUser(mockMvc, username, Role.CLIENT);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, TestUserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", username)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.notes").isArray());
    }

    @Test
    public void therapistMustNotHasDiary() throws Exception {
        String username = "testTherapist";
        TestUserCreator.createUser(mockMvc, username, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, TestUserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", username)
                        .headers(authHeader))
                .andExpect(status().is(404));
    }

    @Test
    public void therapistCantGetDiaryWithoutAccess() throws Exception {
        String username = "therCantGetDiaryWithoutAcc";
        TestUserCreator.createUser(mockMvc, username, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, TestUserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", JEKSVP_USERNAME)
                        .headers(authHeader))
                .andExpect(status().is(403));
    }

    @Test
    public void therapistCanGetDiaryWithAccess() throws Exception {
        HttpHeaders authHeader = tokenObtainer.obtainDefaultAccessedTherapistHeader(mockMvc);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", ACCESSED_CLIENT_USERNAME)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(ACCESSED_CLIENT_USERNAME)))
                .andExpect(jsonPath("$.notes").isArray());
    }
}
