package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.web.dto.request.CreateProgressRequest;
import com.jeksvp.goalkeeper.web.dto.request.UpdateProgressRequest;
import com.jeksvp.goalkeeper.web.dto.response.ProgressResponse;
import com.jeksvp.goalkeeper.service.ProgressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/progresses")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{progressId}")
    public ProgressResponse getProgressById(@PathVariable Long progressId){
        return progressService.findById(progressId);
    }

    @PostMapping
    public ProgressResponse createProgress(@RequestBody CreateProgressRequest request){
        return progressService.createProgress(request);
    }

    @PutMapping("/{progressId}")
    public ProgressResponse updateProgress(@PathVariable Long progressId, @RequestBody UpdateProgressRequest request){
        return progressService.updateProgress(progressId, request);
    }

    @DeleteMapping("/{progressId}")
    public void deleteProgress (@PathVariable Long progressId){
        progressService.deleteById(progressId);
    }
}
