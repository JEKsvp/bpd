package com.jeksvp.bpd.utils.impl;

import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemUtcClock implements ClockSource {

    @Override
    public Clock getClock() {
        return Clock.systemUTC();
    }
}
