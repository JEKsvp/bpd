package com.jeksvp.bpd.configuration;

import com.jeksvp.bpd.security.filter.HttpRequestAccessResolver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ControllerTestConfiguration {

    @Bean
    public HttpRequestAccessResolver httpRequestAccessResolver() {
        return new HttpRequestAccessResolver(null);
    }
}
