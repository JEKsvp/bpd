package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.integration.helpers.TokenObtainer;
import com.jeksvp.bpd.integration.helpers.UserCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.jeksvp.bpd.integration.models.DefaultUser.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenObtainer tokenObtainer;

    @Test
    public void getCurrentUserTest() throws Exception {
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, JEKSVP_USERNAME, JEKSVP_PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/current")
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(JEKSVP_USERNAME)))
                .andExpect(jsonPath("$.email", is(JEKSVP_EMAIL)));
    }

    @Test
    public void userCantGetUserWithoutAccess() throws Exception {
        String username = "anotherUserCantGetEmail";
        UserCreator.createUser(mockMvc, username, Role.CLIENT);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, UserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/${username}", JEKSVP_USERNAME)
                        .headers(authHeader))
                .andExpect(status().is(403));
    }
}
