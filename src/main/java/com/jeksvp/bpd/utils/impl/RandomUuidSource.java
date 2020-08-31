package com.jeksvp.bpd.utils.impl;

import com.jeksvp.bpd.utils.UuidSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomUuidSource implements UuidSource {
    @Override
    public UUID random() {
        return UUID.randomUUID();
    }
}
