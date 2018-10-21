package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.dto.ApiError;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException apiException) {
        ApiError apiError = apiException.getApiError();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
