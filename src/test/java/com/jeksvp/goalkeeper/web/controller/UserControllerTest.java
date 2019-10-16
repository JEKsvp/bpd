package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.service.UserService;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.jeksvp.goalkeeper.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void returnUserById() throws Exception {
        when(userService.getUserByUsername("testUser")).thenReturn(testUser());

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getStringFromFile("/web/controller/user-controller/valid-user-response.json")));
    }

    @Test
    public void returnErrorWhenUserNotFound() throws Exception {
        when(userService.getUserByUsername("testUser"))
                .thenThrow(new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND, "User not found"));

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(getStringFromFile("/web/controller/user-controller/user-not-found-response.json")));
    }

    private UserResponse testUser() {
        return UserResponse.builder()
                .id("ololo")
                .username("test")
                .email("test@mail.ru")
                .roles(Collections.singletonList(Role.USER))
                .build();
    }
}