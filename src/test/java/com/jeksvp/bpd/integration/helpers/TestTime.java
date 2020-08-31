package com.jeksvp.bpd.integration.helpers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TestTime {

    public static final Clock DEFAULT_CLOCK = Clock.fixed(LocalDateTime.of(2020, 8, 20, 20, 20, 20).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
}
