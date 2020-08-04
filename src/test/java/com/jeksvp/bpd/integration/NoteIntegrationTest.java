package com.jeksvp.bpd.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeksvp.bpd.configuration.IntegrationTestConfiguration;
import com.jeksvp.bpd.web.dto.request.SignUpRequest;
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

import static com.jeksvp.bpd.integration.TokenObtainer.JEKSVP_PASSWORD;
import static com.jeksvp.bpd.integration.TokenObtainer.JEKSVP_USERNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class NoteIntegrationTest {

    public static final String PASSWORD = "000000";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenObtainer tokenObtainer;

    private HttpHeaders authHeader;

    @BeforeEach
    public void init() {
        this.authHeader = tokenObtainer.obtainAuthHeader(mockMvc, "ssdaasd", JEKSVP_PASSWORD);
    }

    @Test
    public void createNoteTest() throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/note-controller/create-note-request.json"), Charset.defaultCharset());
        mockMvc.perform(
                post("/api/v1/users/{username}/diary/notes", JEKSVP_USERNAME)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.event.description", is("Тестовое событие из жизы")))
                .andExpect(jsonPath("$.emotionalEvaluation.description", is("Тестовая оценка")))
                .andExpect(jsonPath("$.bodyReaction.description", is("Тестовая реакция тела")))
                .andExpect(jsonPath("$.myThoughts.description", is("Тестовые мысли")))
                .andExpect(jsonPath("$.oppositeThoughts.description", is("Тестовые противоположные мысли")));
    }

    @Test
    public void getNoteTest() throws Exception {
        String noteId = createNoteAndGetId(JEKSVP_USERNAME);
        mockMvc.perform(
                get("/api/v1/users/{username}/diary/notes/{noteId}", JEKSVP_USERNAME, noteId)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(noteId)))
                .andExpect(jsonPath("$.event.description", is("Тестовое событие из жизы")))
                .andExpect(jsonPath("$.emotionalEvaluation.description", is("Тестовая оценка")))
                .andExpect(jsonPath("$.bodyReaction.description", is("Тестовая реакция тела")))
                .andExpect(jsonPath("$.myThoughts.description", is("Тестовые мысли")))
                .andExpect(jsonPath("$.oppositeThoughts.description", is("Тестовые противоположные мысли")));
    }

    @Test
    public void updateNoteTest() throws Exception {
        String username = "UpdatingNoteUser";
        createUser(username);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, PASSWORD);
        this.authHeader = authHeader;
        String noteId = createNoteAndGetId(username);
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/note-controller/update-note-request.json"), Charset.defaultCharset());
        mockMvc.perform(
                put("/api/v1/users/{username}/diary/notes/{noteId}", username, noteId)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(noteId)))
                .andExpect(jsonPath("$.event.description", is("Обновленное тестовое событие из жизы")))
                .andExpect(jsonPath("$.emotionalEvaluation.description", is("Обновленная тестовая оценка")))
                .andExpect(jsonPath("$.bodyReaction.description", is("Обновленная тестовая реакция тела")))
                .andExpect(jsonPath("$.myThoughts.description", is("Обновленные тестовые мысли")))
                .andExpect(jsonPath("$.oppositeThoughts.description", is("Обновленные тестовые противоположные мысли")));
    }

    @Test
    public void deleteNoteTest() throws Exception {
        String noteId = createNoteAndGetId(JEKSVP_USERNAME);

        mockMvc.perform(
                delete("/api/v1/users/{username}/diary/notes/{noteId}", JEKSVP_USERNAME, noteId)
                        .headers(authHeader))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                get("/api/v1/users/{username}/diary/notes/{noteId}", JEKSVP_USERNAME, noteId)
                        .headers(authHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getNotesByDiaryTest() throws Exception {
        String username = "getNotesByDiaryUser";
        createUser(username);
        HttpHeaders authHeader = tokenObtainer.obtainAuthHeader(mockMvc, username, PASSWORD);
        this.authHeader = authHeader;
        String noteId1 = createNoteAndGetId(username);
        String noteId2 = createNoteAndGetId(username);

        mockMvc.perform(
                get("/api/v1/users/{username}/diary/notes", username)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(noteId1)))
                .andExpect(jsonPath("$[1].id", is(noteId2)));
    }

    private String createNoteAndGetId(String username) throws Exception {
        String requestBody = IOUtils.toString(getClass().getResource("/web/controller/note-controller/create-note-request.json"), Charset.defaultCharset());
        String responseBody = mockMvc.perform(
                post("/api/v1/users/{username}/diary/notes", username)
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(responseBody).get("id").toString();
    }

    private void createUser(String username) throws Exception {
        SignUpRequest request = SignUpRequest.builder()
                .username(username)
                .password("000000")
                .email(username + "@mail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(
                post("/api/v1/signup")
                        .headers(authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }
}
