package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DiaryRepository extends MongoRepository<Diary, String> {

    public List<Diary> findDiariesByUsername(String username);
}
