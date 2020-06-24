package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.DiaryRepository;
import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.request.diary.CreateDiaryRequest;
import com.jeksvp.bpd.web.dto.request.diary.UpdateDiaryRequest;
import com.jeksvp.bpd.web.dto.response.DiaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public DiaryResponse getDiary(String id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return DiaryResponse.create(diary);
    }

    @Override
    public DiaryResponse createDiary(CreateDiaryRequest request) {
        Diary diary = Diary.create(request.getName(), SecurityUtils.getCurrentUserName());
        diaryRepository.save(diary);
        return DiaryResponse.create(diary);
    }

    @Override
    public DiaryResponse updateDiary(String id, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        diary.updateName(request.getName());
        Diary updatedDiary = diaryRepository.save(diary);
        return DiaryResponse.create(updatedDiary);
    }

    @Override
    public void deleteDiary(String id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public List<DiaryResponse> getDiariesByUser(String userId) {
        return null;
    }
}
