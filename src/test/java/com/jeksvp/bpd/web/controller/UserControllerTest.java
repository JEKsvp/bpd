package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.TestFileReader;
import com.jeksvp.bpd.configuration.ControllerTestConfiguration;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.service.UserService;
import com.jeksvp.bpd.web.dto.response.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
@ContextConfiguration(classes = ControllerTestConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private final TestFileReader testFileReader = new TestFileReader();

    @Test
    public void returnUserById() throws Exception {
        when(userService.getByUsername("testUser")).thenReturn(testUser());

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/user-controller/valid-user-response.json")));
    }

    @Test
    public void returnErrorWhenUserNotFound() throws Exception {
        when(userService.getByUsername("testUser"))
                .thenThrow(new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND, "User not found"));

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/user-controller/user-not-found-response.json")));
    }

    private UserResponse testUser() {
        return UserResponse.builder()
                .username("test")
                .email("test@mail.ru")
                .roles(Collections.singletonList(Role.CLIENT))
                .build();
    }
}