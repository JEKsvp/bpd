package com.jeksvp.bpd.web.dto.updater.impl;

import com.jeksvp.bpd.domain.entity.diary.Diary;
import com.jeksvp.bpd.web.dto.request.diary.UpdateDiaryRequest;
import com.jeksvp.bpd.web.dto.updater.Updater;
import org.springframework.stereotype.Component;

@Component
public class DiaryUpdater implements Updater<UpdateDiaryRequest, Diary> {

    @Override
    public void update(UpdateDiaryRequest request, Diary diary) {
        diary.updateName(request.getName());
    }
}
