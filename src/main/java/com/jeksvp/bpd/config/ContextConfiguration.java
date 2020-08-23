package com.jeksvp.bpd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ContextConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
