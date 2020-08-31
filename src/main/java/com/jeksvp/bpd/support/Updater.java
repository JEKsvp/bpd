package com.jeksvp.bpd.support;

public interface Updater<Source, Target> {
    void update(Source source, Target target);
}
