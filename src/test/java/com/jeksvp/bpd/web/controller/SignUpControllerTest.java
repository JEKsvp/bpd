package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.TestFileReader;
import com.jeksvp.bpd.configuration.ControllerTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.service.SignUpService;
import com.jeksvp.bpd.web.dto.response.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SignUpController.class)
@ContextConfiguration(classes = ControllerTestConfiguration.class)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignUpService signUpService;

    private final TestFileReader testFileReader = new TestFileReader();

    @Test
    public void invalidShortPasswordTest() throws Exception {
        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-short-password-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-password-response.json")));
    }

    @Test
    public void invalidLongPasswordTest() throws Exception {
        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-long-password-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-password-response.json")));
    }

    @Test
    public void duplicateUsernameTest() throws Exception {
        when(signUpService.registerUser(any()))
                .thenThrow(new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Username duplicateUser exists"));

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-username-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-username-response.json")));
    }

    @Test
    public void invalidCharacterUsernameTest() throws Exception {
        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-character-username-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-character-username-response.json")));
    }

    @Test
    public void duplicateEmailTest() throws Exception {
        when(signUpService.registerUser(any()))
                .thenThrow(new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Email duplicateEmail@test.com exists"));

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-email-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-email-response.json")));
    }

    @Test
    public void emptyRoleTest() throws Exception {
        when(signUpService.registerUser(any()))
                .thenThrow(new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Email duplicateEmail@test.com exists"));

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-role-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-role-response.json")));
    }

    @Test
    public void emptyFirstNameTherapistTest() throws Exception {
        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-first-name-therapist-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-first-name-therapist-response.json")));
    }

    @Test
    public void emptyLastNameTherapistTest() throws Exception {
        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-last-name-therapist-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/empty-last-name-therapist-response.json")));
    }

    @Test
    public void validSignUp() throws Exception {
        when(signUpService.registerUser(any()))
                .thenReturn(buildUserResponse());

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/valid-sign-up-as-client-request.json")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/valid-sign-up-as-client-response.json")));
    }

    private UserResponse buildUserResponse() {
        return UserResponse.builder()
                .email("abadeksvp@gmail.com")
                .roles(Collections.singletonList(Role.CLIENT))
                .username("jeksvp")
                .build();
    }
}