package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.diary.CreateDiaryRequest;
import com.jeksvp.bpd.web.dto.request.diary.UpdateDiaryRequest;
import com.jeksvp.bpd.web.dto.response.DiaryResponse;

import java.util.List;

public interface DiaryService {

    DiaryResponse getDiary(String id);
    DiaryResponse createDiary(CreateDiaryRequest request);
    DiaryResponse updateDiary(String id, UpdateDiaryRequest updateDiaryRequest);
    void deleteDiary(String id);

    List<DiaryResponse> getDiariesByUser(String userId);
}
