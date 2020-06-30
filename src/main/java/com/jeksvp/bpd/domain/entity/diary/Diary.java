package com.jeksvp.bpd.domain.entity.diary;


import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.diary.NoteNotFoundException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Document
@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Diary {

    @Id
    private final String id;

    private final String username;

    private String name;

    private List<Note> notes = new ArrayList<>();

    public static Diary create(String name, String username) {
        return Diary.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .username(username)
                .notes(new ArrayList<>())
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(String noteId) {
        notes.removeIf(note -> noteId.equals(note.getId()));
    }
}
