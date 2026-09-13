package com.focusflow.service;

public class SessionAlreadyRunningException extends RuntimeException {

    public SessionAlreadyRunningException(String message) {
        super(message);
    }
}
