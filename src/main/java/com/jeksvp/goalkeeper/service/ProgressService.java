package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.web.dto.request.CreateProgressRequest;
import com.jeksvp.goalkeeper.web.dto.request.UpdateProgressRequest;
import com.jeksvp.goalkeeper.web.dto.response.ProgressResponse;

public interface ProgressService {

    ProgressResponse findById(Long id);

    ProgressResponse createProgress(CreateProgressRequest request);

    ProgressResponse updateProgress(Long progressId, UpdateProgressRequest request);

    void deleteById(Long progressId);
}
