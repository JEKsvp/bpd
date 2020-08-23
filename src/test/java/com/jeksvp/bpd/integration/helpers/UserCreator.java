package com.jeksvp.bpd.integration.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.web.dto.request.SignUpRequest;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCreator {

    public static String PASSWORD = "000000";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static void createUser(MockMvc mockMvc, String username, Role role) {
        SignUpRequest request = SignUpRequest.builder()
                .username(username)
                .password(PASSWORD)
                .email(username + "@mail.com")
                .firstName(username + " fist name")
                .lastName(username + " last name")
                .role(role)
                .build();
        mockMvc.perform(
                post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }
}
