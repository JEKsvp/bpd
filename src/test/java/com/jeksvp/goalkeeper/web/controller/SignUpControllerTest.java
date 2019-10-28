package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.TestFileReader;
import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.service.SignUpService;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignUpService signUpService;

    private final TestFileReader testFileReader = new TestFileReader();

    @Test
    public void invalidShortPasswordTest() throws Exception {

        ResultMatcher json = content()
                .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-password-response.json"));

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-short-password-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-password-response.json")));
    }

    @Test
    public void invalidLongPasswordTest() throws Exception {

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-long-password-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/invalid-password-response.json")));
    }

    @Test
    public void duplicateUsernameTest() throws Exception {
        when(signUpService.registerUser(any()))
                .thenThrow(new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Username duplicateUser exists"));

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-username-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-username-response.json")));
    }

    @Test
    public void duplicateEmailTest() throws Exception {
        when(signUpService.registerUser(any()))
                .thenThrow(new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Email duplicateEmail@test.com exists"));

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-email-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/duplicate-email-response.json")));
    }

    @Test
    public void validSignUp() throws Exception {
        when(signUpService.registerUser(any()))
                .thenReturn(buildUserResponse());

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/signup-controller/valid-sign-up-request.json")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/signup-controller/valid-sign-up-response.json")));
    }

    private UserResponse buildUserResponse() {
        return UserResponse.builder()
                .id("testId")
                .email("abadeksvp@gmail.com")
                .roles(Collections.singletonList(Role.USER))
                .username("jeksvp")
                .build();
    }
}