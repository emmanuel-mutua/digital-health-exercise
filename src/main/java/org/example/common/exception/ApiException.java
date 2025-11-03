package org.example.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final boolean success = false;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private String code="ERROR";
    private final String debugMessage;

    public ApiException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.debugMessage = null;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.debugMessage = cause != null ? cause.getMessage() : null;
    }

    public ApiException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.code = "BAD_REQUEST";
        this.debugMessage = message;
    }
}