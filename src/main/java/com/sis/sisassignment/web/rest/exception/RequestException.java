package com.sis.sisassignment.web.rest.exception;

import lombok.Getter;

@Getter
public class RequestException extends Exception {
    private final int code;

    private final String message;

    public RequestException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
