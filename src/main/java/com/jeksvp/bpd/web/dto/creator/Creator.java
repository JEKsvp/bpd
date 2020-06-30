package com.jeksvp.bpd.web.dto.creator;

public interface Creator<Source, Target> {
    Target create(Source source);
}
