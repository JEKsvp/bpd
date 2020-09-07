package com.jeksvp.bpd.domain.entity.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionalRoutesTest {

    @Test
    public void exceptionalRouteTest() {
        for (ExceptionalRoutes exceptionalRoute : ExceptionalRoutes.values()) {
            assertTrue(ExceptionalRoutes.isRouteExceptional(exceptionalRoute.getRoute()));
        }
    }

    @Test
    public void unexceptionalRouteTest() {
        assertFalse(ExceptionalRoutes.isRouteExceptional("/api/v1/users/jeksvp"));
        assertFalse(ExceptionalRoutes.isRouteExceptional("/api/v1/users/jeksvp/diary"));
        assertFalse(ExceptionalRoutes.isRouteExceptional("/api/v1/users/{username}/diary/notes"));
        assertFalse(ExceptionalRoutes.isRouteExceptional("/api/v1/users/{username}/diary/notes/1"));
    }

}