package com.github.mkopylec.errorest;

import org.springframework.http.HttpStatus;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

public abstract class RestException extends RuntimeException {

    protected final String errorCode;
    protected final HttpStatus responseHttpStatus;
    protected final LoggingLevel loggingLevel;
    protected final boolean logStackTrace;

    protected RestException(String errorCode, String errorDescription, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, boolean logStackTrace) {
        super(errorDescription);
        hasText(errorCode, "Empty exception error code");
        notNull(responseHttpStatus, "Empty exception response HTTP status");
        notNull(loggingLevel, "Empty exception logging level");
        this.errorCode = errorCode;
        this.responseHttpStatus = responseHttpStatus;
        this.loggingLevel = loggingLevel;
        this.logStackTrace = logStackTrace;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return getMessage();
    }

    public HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }
}
