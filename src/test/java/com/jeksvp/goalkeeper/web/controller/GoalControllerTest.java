package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.TestFileReader;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.service.GoalService;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.web.dto.response.ProgressResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GoalController.class)
public class GoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoalService goalService;
    
    private final TestFileReader testFileReader = new TestFileReader();
    
    @Test
    public void getGoalByIdTest() throws Exception {
        when(goalService.findById(eq("testGoalId")))
                .thenReturn(buildValidGoalResponse());

        this.mockMvc.perform(get("/api/v1/goals/{goalId}", "testGoalId")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goal-response.json")));
    }

    @Test
    public void getGoalNotFoundTest() throws Exception {
        when(goalService.findById(eq("testGoalId")))
                .thenThrow(new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));

        this.mockMvc.perform(get("/api/v1/goals/{goalId}", "testGoalId")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/not-found-response.json")));
    }

    @Test
    public void deleteGoalNotFoundTest() throws Exception {
        doThrow(new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND))
                .when(goalService).deleteById("testGoalId");

        this.mockMvc.perform(delete("/api/v1/goals/{goalId}", "testGoalId")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/not-found-response.json")));
    }

    @Test
    public void getAllGoalsTest() throws Exception {
        when(goalService.findAll()).thenReturn(Collections.singletonList(buildValidGoalResponse()));

        this.mockMvc.perform(get("/api/v1/goals")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goals-response.json")));
        verify(goalService, times(1)).findAll();
    }

    @Test
    public void getGoalsByUsernameTest() throws Exception {
        when(goalService.findByUsername("testUser"))
                .thenReturn(Collections.singletonList(buildValidGoalResponse()));

        this.mockMvc.perform(get("/api/v1/goals")
                .param("username", "testUser")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goals-response.json")));
        verify(goalService, times(1)).findByUsername("testUser");
    }

    @Test
    public void createGoalTest() throws Exception {
        when(goalService.createGoal(any()))
                .thenReturn(buildValidGoalResponse());

        this.mockMvc.perform(post("/api/v1/goals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goal-request.json")))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goal-response.json")));
        verify(goalService, times(1)).createGoal(any());
    }

    @Test
    public void updateGoalTest() throws Exception {
        when(goalService.updateGoal(eq("testGoalId"), any()))
                .thenReturn(buildValidGoalResponse());

        this.mockMvc.perform(put("/api/v1/goals/{goalId}", "testGoalId")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goal-request.json")))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/valid-goal-response.json")));
        verify(goalService, times(1)).updateGoal(eq("testGoalId"), any());
    }

    @Test
    public void emptyProgressesGoalTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/goals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-progresses-goal-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-progresses-response.json")));
    }

    @Test
    public void emptyNameGoalTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/goals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-name-goal-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-name-response.json")));
    }

    @Test
    public void emptyExpirationDateGoalTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/goals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-expiration-date-goal-request.json")))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .json(testFileReader.getStringFromFile("/web/controller/goal-controller/empty-expiration-date-response.json")));
    }



    private GoalResponse buildValidGoalResponse() {
        return GoalResponse.builder()
                .id("testGoalId")
                .name("testName")
                .description("testDescription")
                .createDate(LocalDateTime.parse("1995-11-02T03:08:55"))
                .expirationDate(LocalDateTime.parse("2020-11-02T03:08:55"))
                .progresses(Collections.singletonList(
                        ProgressResponse.builder()
                                .name("kek")
                                .currentValue(new BigDecimal(8.8))
                                .maxValue(new BigDecimal(9.9))
                                .build()
                )).build();
    }
}