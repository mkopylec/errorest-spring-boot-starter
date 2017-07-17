package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.http.HttpStatus;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

public abstract class RestApplicationException extends RuntimeException {

    protected final String errorCode;
    protected final HttpStatus responseHttpStatus;
    protected final LoggingLevel loggingLevel;
    protected final boolean logStackTrace;

    public RestApplicationException(RestExceptionData data) {
        this(data.getErrorCode(), data.getErrorDescription(), data.getResponseHttpStatus(), data.getLoggingLevel(), data.isLogStackTrace(), data.getCause());
    }

    public RestApplicationException(String errorCode, String errorDescription, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, boolean logStackTrace) {
        this(errorCode, errorDescription, responseHttpStatus, loggingLevel, logStackTrace, null);
    }

    public RestApplicationException(String errorCode, String errorDescription, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, Throwable cause) {
        this(errorCode, errorDescription, responseHttpStatus, loggingLevel, true, cause);
    }

    private RestApplicationException(String errorCode, String errorDescription, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, boolean logStackTrace, Throwable cause) {
        super(errorDescription, cause);
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
