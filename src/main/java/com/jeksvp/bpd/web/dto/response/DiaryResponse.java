package com.jeksvp.bpd.web.dto.response;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.domain.entity.diary.Note;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DiaryResponse {
    private String id;
    private String name;
    private String username;
    private List<String> notes = new ArrayList<>();

    public static DiaryResponse create(Diary diary) {
        return DiaryResponse.builder()
                .id(diary.getId())
                .name(diary.getName())
                .username(diary.getUsername())
                .notes(diary.getNotes().stream()
                        .map(Note::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
