package com.jeksvp.bpd.integration;


import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static com.jeksvp.bpd.integration.DefaultClient.CLIENT_ID;
import static com.jeksvp.bpd.integration.DefaultClient.SECRET;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SingUpIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void singUpAsClientAndGetTokenTest() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-as-client-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-as-client-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(responseBody));

        mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(CLIENT_ID, SECRET))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("grant_type=password&username=jeksvp&password=111111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("refresh_token").exists());
    }

    @Test
    public void singUpAsTherapistAndGetTokenTest() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-as-therapist-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-as-therapist-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(responseBody));

        mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(CLIENT_ID, SECRET))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("grant_type=password&username=psycho&password=111111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("refresh_token").exists());
    }

    @Test
    public void cantCreateUserWithUnacceptableName() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/unacceptable-username-sign-up-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/unacceptable-username-sign-up-response.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(responseBody));
    }
}
