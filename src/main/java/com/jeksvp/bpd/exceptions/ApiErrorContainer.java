package com.jeksvp.bpd.exceptions;

import org.springframework.http.HttpStatus;

public enum ApiErrorContainer {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "Diary not found"),
    DIARY_ALREADY_EXISTS(HttpStatus.CONFLICT, "Diary already exists"),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "Note not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    THERAPISTS_ACCESS_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Therapist access list not found"),
    CLIENT_ACCESS_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Client access list not found"),
    THERE_IS_NO_PENDING_REQUEST(HttpStatus.CONFLICT, "There is no PENDING request"),
    CLIENT_CANT_ACCEPT_ACCESS_REQUEST(HttpStatus.CONFLICT, "Client can't ACCEPT access request"),
    CLIENT_CANT_DECLINE_ACCESS_REQUEST(HttpStatus.CONFLICT, "Client can't DECLINE access request"),
    THERAPIST_CANT_PENDING_ACCESS_REQUEST(HttpStatus.CONFLICT, "Therapist can't PENDING access request"),
    CLIENT_ALREADY_HAS_THERAPIST(HttpStatus.CONFLICT, "Client already has therapist"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access denied");

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
