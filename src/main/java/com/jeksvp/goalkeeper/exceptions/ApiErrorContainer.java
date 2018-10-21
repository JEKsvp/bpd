package com.jeksvp.goalkeeper.exceptions;

import org.springframework.http.HttpStatus;

public enum ApiErrorContainer {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error");

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
