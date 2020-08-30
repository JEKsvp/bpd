package com.jeksvp.bpd.domain.entity.access;

public class DuplicateAccessException extends RuntimeException{
    public DuplicateAccessException() {
        super();
    }

    public DuplicateAccessException(String message) {
        super(message);
    }

    public DuplicateAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAccessException(Throwable cause) {
        super(cause);
    }

    protected DuplicateAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
