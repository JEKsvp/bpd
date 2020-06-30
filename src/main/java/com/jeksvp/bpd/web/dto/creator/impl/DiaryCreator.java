package com.jeksvp.bpd.web.dto.creator.impl;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.creator.Creator;
import com.jeksvp.bpd.web.dto.request.diary.CreateDiaryRequest;
import org.springframework.stereotype.Component;

@Component
public class DiaryCreator implements Creator<CreateDiaryRequest, Diary> {

    @Override
    public Diary create(CreateDiaryRequest request) {
        return Diary.create(request.getName(), SecurityUtils.getCurrentUserName());
    }
}
