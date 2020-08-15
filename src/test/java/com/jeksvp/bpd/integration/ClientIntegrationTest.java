package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
public class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenObtainer tokenObtainer;

    @Test
    public void emptyClientsListForTherapistAfterSignUp() throws Exception {
        String username = "emptyClientsTherapistSignUp";
        UserCreator.createUser(mockMvc, username, Role.THERAPIST);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, UserCreator.PASSWORD);
        mockMvc.perform(
                get("/api/v1/users/current/clients")
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[]"));
    }

    @Test
    public void notFoundClientListForClientAfterSignUp() throws Exception {
        String username = "404ClientsClientSignUp";
        UserCreator.createUser(mockMvc, username, Role.CLIENT);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, UserCreator.PASSWORD);

        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/client-controller/clients-not-found-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                get("/api/v1/users/current/clients")
                        .headers(authHeader))
                .andExpect(status().is(404))
                .andExpect(content().json(responseBody));
    }
}
