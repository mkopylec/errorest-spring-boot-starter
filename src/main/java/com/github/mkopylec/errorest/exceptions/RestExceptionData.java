package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.http.HttpStatus;

public class RestExceptionData {

    protected String errorCode;
    protected String errorDescription;
    protected HttpStatus responseHttpStatus;
    protected LoggingLevel loggingLevel;
    protected boolean logStackTrace;
    protected Throwable cause;

    public String getErrorCode() {
        return errorCode;
    }

    public RestExceptionData withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public RestExceptionData withErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public RestExceptionData withResponseHttpStatus(HttpStatus responseHttpStatus) {
        this.responseHttpStatus = responseHttpStatus;
        return this;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public RestExceptionData withLoggingLevel(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
        return this;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }

    public RestExceptionData withLogStackTrace() {
        this.logStackTrace = true;
        return this;
    }

    public Throwable getCause() {
        return cause;
    }

    public RestExceptionData withCause(Throwable cause) {
        if (cause != null) {
            logStackTrace = true;
        }
        this.cause = cause;
        return this;
    }
}
