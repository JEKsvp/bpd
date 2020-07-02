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
    public void createDiaryTest() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/diary-controller/create-diary-request.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/diaries")
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", is("Мой тестовый дневник")))
                .andExpect(jsonPath("$.username", is(JEKSVP_USERNAME)));
    }

    @Test
    public void getDiaryTest() throws Exception {
        String diaryId = createDiaryAndGetId();
        mockMvc.perform(
                get("/api/v1/diaries/{id}", diaryId)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(diaryId)))
                .andExpect(jsonPath("$.name", is("Мой тестовый дневник")))
                .andExpect(jsonPath("$.username", is(JEKSVP_USERNAME)));

    }

    @Test
    public void updateDiaryTest() throws Exception {
        String diaryId = createDiaryAndGetId();
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/diary-controller/update-diary-request.json"), Charset.defaultCharset());
        mockMvc.perform(
                put("/api/v1/diaries/{id}", diaryId)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(diaryId)))
                .andExpect(jsonPath("$.name", is("Мой обновленный тестовый дневник")))
                .andExpect(jsonPath("$.username", is(JEKSVP_USERNAME)));
    }

    @Test
    public void deleteDiaryTest() throws Exception {
        String diaryId = createDiaryAndGetId();
        mockMvc.perform(
                delete("/api/v1/diaries/{id}", diaryId)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                get("/api/v1/diaries/{id}", diaryId)
                        .headers(authHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDiariesByUsernameTest() throws Exception {
        createDiaryAndGetId();
        mockMvc.perform(
                get("/api/v1/diaries")
                        .param("username", "jeksvp")
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].username", is("jeksvp")));
    }

    private String createDiaryAndGetId() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/diary-controller/create-diary-request.json"), Charset.defaultCharset());
        String responseBody = mockMvc.perform(
                post("/api/v1/diaries")
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(responseBody).get("id").toString();
    }
}
