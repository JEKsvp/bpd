package com.jeksvp.bpd.service.signupinitializer;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.service.SignUpInitializer;
import org.springframework.stereotype.Component;

@Component
public class DiaryInitializer implements SignUpInitializer {

    private final DiaryService diaryService;

    public DiaryInitializer(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @Override
    public boolean shouldCreate(User user) {
        return user.getRoles().contains(Role.CLIENT);
    }

    @Override
    public void createEntityFor(User user) {
        diaryService.createDiary(user.getUsername());
    }
}
