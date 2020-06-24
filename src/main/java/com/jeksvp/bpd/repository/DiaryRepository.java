package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryRepository extends MongoRepository<Diary, String> {
}
