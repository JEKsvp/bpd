package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.web.dto.request.diary.CreateDiaryRequest;
import com.jeksvp.bpd.web.dto.request.diary.UpdateDiaryRequest;
import com.jeksvp.bpd.web.dto.response.DiaryResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping
    public List<DiaryResponse> getDiariesByUsername(@RequestParam String username) {
        return diaryService.getDiariesByUsername(username);
    }

    @GetMapping("/{id}")
    public DiaryResponse getDiaryById(@PathVariable String id) {
        return diaryService.getDiary(id);
    }

    @PostMapping
    public DiaryResponse createDiary(@RequestBody @Valid CreateDiaryRequest createDiaryRequest) {
        return diaryService.createDiary(createDiaryRequest);
    }

    @PutMapping("/{id}")
    public DiaryResponse updateDiary(@PathVariable String id,
                                     @RequestBody @Valid UpdateDiaryRequest updateDiaryRequest) {
        return diaryService.updateDiary(id, updateDiaryRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable String id) {
        diaryService.deleteDiary(id);
    }

}
