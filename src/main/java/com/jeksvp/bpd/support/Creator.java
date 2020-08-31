package com.jeksvp.bpd.support;

public interface Creator<Source, Target> {
    Target create(Source source);
}
