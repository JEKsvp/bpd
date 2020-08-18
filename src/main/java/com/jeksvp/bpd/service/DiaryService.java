package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.diary.DiaryResponse;

public interface DiaryService {

    DiaryResponse getDiaryByUsername(String username);

    DiaryResponse createDiary(String username);
}
