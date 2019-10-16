package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.domain.entity.Goal;
import com.jeksvp.goalkeeper.domain.entity.Progress;
import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.domain.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.GoalRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.GoalService;
import com.jeksvp.goalkeeper.web.dto.request.GoalRequest;
import com.jeksvp.goalkeeper.web.dto.request.ProgressRequest;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.web.dto.response.ProgressResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GoalServiceImplTest {

    public static final String TEST_GOAL_ID = "test goal id";
    public static final String USERNAME = "jeksvp";
    private final LocalDateTime EXPIRATION_DATE = LocalDateTime.parse("2020-01-02T03:03:03");

    @MockBean
    private GoalRepository goalRepository;

    @MockBean
    private UserRepository userRepository;

    private GoalService goalService;

    @Before
    public void init() {
        this.goalService = new GoalServiceImpl(goalRepository, userRepository);
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void findByUsernameWithUserNotFoundGoal() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.findByUsername(USERNAME));
        assertEquals(ApiErrorContainer.USER_NOT_FOUND.getMessage(), apiException.getMessage());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void findByUsernameWithEmptyList() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(buildUser());
        when(goalRepository.findByUsername(USERNAME)).thenReturn(Collections.emptyList());

        List<GoalResponse> goals = goalService.findByUsername(USERNAME);
        assertEquals(0, goals.size());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void findWithNotFoundGoal() {
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.findById(TEST_GOAL_ID));
        assertEquals(ApiErrorContainer.RESOURCE_NOT_FOUND.getMessage(), apiException.getMessage());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void createGoalTest() {
        when(goalRepository.save(any())).thenReturn(buildExpectedSavedGoal());
        when(userRepository.findByUsername(USERNAME)).thenReturn(buildUser());

        GoalResponse actual = goalService.createGoal(buildSaveGoalRequest());
        GoalResponse expected = buildExpectedSavedGoalResponse();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getProgresses(), actual.getProgresses());
        verify(userRepository, times(1)).findByUsername(eq(USERNAME));
        verify(goalRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void notFoundUserTest() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.createGoal(buildSaveGoalRequest()));
        assertEquals(ApiErrorContainer.USER_NOT_FOUND.getMessage(), apiException.getMessage());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void updateGoalTest() {
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.ofNullable(buildExpectedSavedGoal()));
        when(goalRepository.save(any())).thenReturn(buildExpectedUpdatedGoal());

        GoalResponse actual = goalService.updateGoal(TEST_GOAL_ID, buildUpdateGoalRequest());
        GoalResponse expected = buildExpectedUpdatedGoalResponse();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getProgresses(), actual.getProgresses());
        assertEquals(expected.getProgresses(), actual.getProgresses());
        verify(goalRepository, times(1)).findById(eq(TEST_GOAL_ID));
        verify(goalRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "wrongUser")
    public void updateWithUnacceptableUser() {
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.of(buildExpectedSavedGoal()));

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.updateGoal(TEST_GOAL_ID, buildUpdateGoalRequest()));
        assertEquals(ApiErrorContainer.ACCESS_DENIED.getMessage(), apiException.getMessage());
    }

    @Test
    @WithMockUser(username = USERNAME)
    public void deleteWithNotFoundGoal() {
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.deleteById(TEST_GOAL_ID));
        assertEquals(ApiErrorContainer.RESOURCE_NOT_FOUND.getMessage(), apiException.getMessage());
    }

    @Test
    @WithMockUser(username = "wrongUser")
    public void deleteWithUnacceptableUser() {
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.ofNullable(buildExpectedSavedGoal()));

        ApiException apiException = assertThrows(ApiException.class,
                () -> goalService.deleteById(TEST_GOAL_ID));
        assertEquals(ApiErrorContainer.ACCESS_DENIED.getMessage(), apiException.getMessage());
    }

    private GoalRequest buildSaveGoalRequest() {
        return GoalRequest.builder()
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .progresses(Collections.singletonList(
                        ProgressRequest.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(5))
                                .build())
                ).build();
    }

    private GoalRequest buildUpdateGoalRequest() {
        return GoalRequest.builder()
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .progresses(Collections.singletonList(
                        ProgressRequest.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(8))
                                .build())
                ).build();
    }

    private Optional<User> buildUser() {
        return Optional.of(
                User.builder()
                        .id("ololo")
                        .username(USERNAME)
                        .password("lolkek")
                        .email("ololo@azaza.com")
                        .roles(Collections.singletonList(Role.USER))
                        .build()
        );
    }

    private GoalResponse buildExpectedSavedGoalResponse() {
        return GoalResponse.builder()
                .id(TEST_GOAL_ID)
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .progresses(Collections.singletonList(
                        ProgressResponse.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(5))
                                .build())
                ).build();
    }

    private GoalResponse buildExpectedUpdatedGoalResponse() {
        return GoalResponse.builder()
                .id(TEST_GOAL_ID)
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .progresses(Collections.singletonList(
                        ProgressResponse.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(8))
                                .build())
                ).build();
    }

    private Goal buildExpectedSavedGoal() {
        return Goal.builder()
                .id("test goal id")
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .username(USERNAME)
                .progresses(Collections.singletonList(
                        Progress.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(5))
                                .build())
                ).build();
    }

    private Goal buildExpectedUpdatedGoal() {
        return Goal.builder()
                .id("test goal id")
                .name("test name")
                .description("test description")
                .expirationDate(EXPIRATION_DATE)
                .progresses(Collections.singletonList(
                        Progress.builder()
                                .name("test progress")
                                .maxValue(new BigDecimal(10))
                                .currentValue(new BigDecimal(8))
                                .build())
                ).build();
    }

}