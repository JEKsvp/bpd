package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.controller.UserController;
import com.jeksvp.goalkeeper.domain.entity.User;
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

import static com.jeksvp.goalkeeper.TestUtils.getStringFromFile;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserEntityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void returnUserById() throws Exception {
        given(userService.getUserByUserName("testUser")).willReturn(testUser());

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getStringFromFile("/web/controller/UserController/test-user.json")));
    }

    @Test
    public void returnErrorWhenUserNotFound() throws Exception {
        given(userService.getUserByUserName("testUser"))
                .willThrow(new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND, "User not found"));

        mvc.perform(get("/api/v1/users/{username}", "testUser"))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(getStringFromFile("/web/controller/UserController/user-not-found-error.json")));
    }

    private UserResponse testUser() {
        UserResponse user = new UserResponse();
        user.setEmail("test@mail.ru");
        user.setId(12L);
        user.setUsername("test");
        return user;
    }
}