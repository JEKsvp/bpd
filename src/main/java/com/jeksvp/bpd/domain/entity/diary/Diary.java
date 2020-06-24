package com.jeksvp.bpd.domain.entity.diary;


import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.diary.NoteNotFoundException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public Diary updateName(String name) {
        this.name = name;
        return this;
    }

    public Diary updateNote(String id, Note note) {
        this.notes.stream()
                .filter(n -> id.equals(n.getId()))
                .findFirst()
                .orElseThrow(NoteNotFoundException::new)
                .update(note);
        return this;
    }

    public Diary createNote(Note note) {
        notes.add(Note.create(note));
        return this;
    }
}
