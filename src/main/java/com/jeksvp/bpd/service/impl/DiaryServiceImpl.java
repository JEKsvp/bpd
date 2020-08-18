package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.DiaryRepository;
import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.web.dto.response.diary.DiaryResponse;
import org.springframework.stereotype.Service;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public DiaryResponse getDiaryByUsername(String username) {
        return diaryRepository.findById(username)
                .map(DiaryResponse::create)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.DIARY_NOT_FOUND));
    }

    @Override
    public DiaryResponse createDiary(String username) {
        if (diaryRepository.existsById(username)) {
            throw new ApiException(ApiErrorContainer.DIARY_ALREADY_EXISTS);
        }
        Diary diary = diaryRepository.save(Diary.create(username));
        return DiaryResponse.create(diary);
    }
}
