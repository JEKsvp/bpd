package com.jeksvp.bpd.domain.entity.diary;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Diary {

    @Id
    private String username;

    private List<Note> notes = new ArrayList<>();

    public static Diary create(String username) {
        return Diary.builder()
                .username(username)
                .notes(new ArrayList<>())
                .build();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(String noteId) {
        notes.removeIf(note -> noteId.equals(note.getId()));
    }
}
