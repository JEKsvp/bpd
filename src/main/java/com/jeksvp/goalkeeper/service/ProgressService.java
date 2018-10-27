package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.dto.request.CreateProgressRequest;
import com.jeksvp.goalkeeper.dto.request.UpdateProgressRequest;
import com.jeksvp.goalkeeper.dto.response.ProgressResponse;

public interface ProgressService {

    ProgressResponse findById(Long id);

    ProgressResponse createProgress(CreateProgressRequest request);

    ProgressResponse updateProgress(Long progressId, UpdateProgressRequest request);

    void deleteById(Long progressId);
}
