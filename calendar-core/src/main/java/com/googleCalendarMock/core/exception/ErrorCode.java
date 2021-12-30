package com.googleCalendarMock.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PASSWORD_NOT_MATCH("password not match"),
    ALREADY_EXISTS_USER("account already exists"),
    USER_NOT_FOUND("user not found"),
    VALIDATION_FAIL("not valid"),
    BAD_REQUEST("bad request"),
    EVENT_CREATE_OVERLAPPED_PERIOD("overlapped event period");

    private final String message;
    ErrorCode(String message) {
        this.message = message;
    }
}
