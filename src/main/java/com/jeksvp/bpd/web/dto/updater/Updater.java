package com.jeksvp.bpd.web.dto.updater;

public interface Updater<Source, Target> {
    void update(Source source, Target target);
}
