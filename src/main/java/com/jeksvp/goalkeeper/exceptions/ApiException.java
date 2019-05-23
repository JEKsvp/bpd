package com.jeksvp.goalkeeper.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public ApiException(ApiErrorContainer apiErrorContainer) {
        this.message = apiErrorContainer.getMessage();
        this.httpStatus = apiErrorContainer.getHttpStatus();
    }

    public ApiException(ApiErrorContainer apiErrorContainer, String message) {
        this.message = message;
        this.httpStatus = apiErrorContainer.getHttpStatus();
    }
}
