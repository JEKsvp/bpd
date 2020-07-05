package com.jeksvp.bpd.exceptions;

import org.springframework.http.HttpStatus;

public enum ApiErrorContainer {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "Diary not found"),
    DIARY_ALREADY_EXISTS(HttpStatus.CONFLICT, "Diary already exists"),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "Note not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied");

    private HttpStatus httpStatus;
    private String message;

    ApiErrorContainer(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
