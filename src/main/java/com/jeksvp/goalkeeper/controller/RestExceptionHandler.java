package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.dto.ApiError;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException apiException) {
        ApiError apiError = apiException.getApiError();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String field = ex.getBindingResult().getFieldError().getField();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiError apiError = new ApiError(MessageFormat.format("Invalid field <{0}>. {1}", field, message), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
