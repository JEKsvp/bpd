package com.jeksvp.bpd.integration;

import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static com.jeksvp.bpd.integration.TokenObtainer.JEKSVP_USERNAME;
import static com.jeksvp.bpd.integration.TokenObtainer.JEKSVP_PASSWORD;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private HttpHeaders authHeader;

    @BeforeEach
    public void init() {
        this.authHeader = tokenObtainer.obtainAuthHeader(mockMvc, JEKSVP_USERNAME, JEKSVP_PASSWORD);
    }

    @Test
    public void getDiaryTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/{username}/diary", JEKSVP_USERNAME)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(JEKSVP_USERNAME)))
                .andExpect(jsonPath("$.notes").isArray());

    }
}
