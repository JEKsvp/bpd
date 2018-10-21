package com.jeksvp.goalkeeper.exceptions;

import com.jeksvp.goalkeeper.dto.ApiError;

public class ApiException extends RuntimeException {

    private ApiError apiError;

    public ApiException(ApiErrorContainer apiErrorContainer) {
        this.apiError = new ApiError();
        this.apiError.setMessage(apiErrorContainer.getMessage());
        this.apiError.setStatus(apiErrorContainer.getHttpStatus());
    }

    public ApiException(ApiErrorContainer apiErrorContainer, String message) {
        this.apiError = new ApiError();
        this.apiError.setMessage(message);
        this.apiError.setStatus(apiErrorContainer.getHttpStatus());
    }

    public ApiError getApiError() {
        return apiError;
    }
}
