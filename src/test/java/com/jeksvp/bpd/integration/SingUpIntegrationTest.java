package com.jeksvp.bpd.integration;


import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SingUpIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void singUpAndGetTokenTest() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-request.json"), Charset.defaultCharset());
        String responseBody = IOUtils.toString(getClass().getResource("/web/controller/signup-controller/valid-sign-up-response.json"), Charset.defaultCharset());
        String basicAuth = "Basic " + HttpHeaders.encodeBasicAuth("clientid", "secret", Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(responseBody));

        mockMvc.perform(
                post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", basicAuth)
                        .content("grant_type=password&username=jeksvp&password=111111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("refresh_token").exists());
    }
}
