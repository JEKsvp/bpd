package com.jeksvp.bpd.web.dto.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Source, Target> {
    Target map(Source source);

    default List<Target> map(Collection<Source> sources) {
        return sources.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
