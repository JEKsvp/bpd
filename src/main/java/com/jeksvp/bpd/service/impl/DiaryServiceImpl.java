package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.DiaryRepository;
import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.web.dto.creator.Creator;
import com.jeksvp.bpd.web.dto.request.diary.CreateDiaryRequest;
import com.jeksvp.bpd.web.dto.request.diary.UpdateDiaryRequest;
import com.jeksvp.bpd.web.dto.response.DiaryResponse;
import com.jeksvp.bpd.web.dto.updater.Updater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final Creator<CreateDiaryRequest, Diary> diaryCreator;
    private final Updater<UpdateDiaryRequest, Diary> diaryUpdater;

    public DiaryServiceImpl(DiaryRepository diaryRepository,
                            Creator<CreateDiaryRequest, Diary> diaryCreator,
                            Updater<UpdateDiaryRequest, Diary> diaryUpdater) {
        this.diaryRepository = diaryRepository;
        this.diaryCreator = diaryCreator;
        this.diaryUpdater = diaryUpdater;
    }

    @Override
    public DiaryResponse getDiary(String id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.DIARY_NOT_FOUND));
        return DiaryResponse.create(diary);
    }

    @Override
    public DiaryResponse createDiary(CreateDiaryRequest request) {
        Diary diary = diaryCreator.create(request);
        diaryRepository.save(diary);
        return DiaryResponse.create(diary);
    }

    @Override
    public DiaryResponse updateDiary(String id, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.DIARY_NOT_FOUND));
        diaryUpdater.update(request, diary);
        Diary updatedDiary = diaryRepository.save(diary);
        return DiaryResponse.create(updatedDiary);
    }

    @Override
    public void deleteDiary(String id) {
        if (diaryRepository.existsById(id)) {
            diaryRepository.deleteById(id);
        } else {
            throw new ApiException(ApiErrorContainer.DIARY_NOT_FOUND);
        }
    }

    @Override
    public List<DiaryResponse> getDiariesByUser(String userId) {
        return null;
    }
}
