package com.github.mkopylec.errorest;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {

    protected final String errorCode;
    protected final HttpStatus httpStatus;
    protected final LogLevel logLevel;
    protected final boolean logStackTrace;

    protected RestException(String errorCode, String errorDescription, HttpStatus httpStatus, LogLevel logLevel, boolean logStackTrace) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
        this.logStackTrace = logStackTrace;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }
}
