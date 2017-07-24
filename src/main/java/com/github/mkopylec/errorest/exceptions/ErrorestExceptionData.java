package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorestExceptionData {

    protected String message;
    protected final List<Error> errors = new ErrorsLoggingList();
    protected HttpStatus responseHttpStatus;
    protected LoggingLevel loggingLevel;
    protected boolean logStackTrace;
    protected Throwable cause;

    public String getMessage() {
        return message;
    }

    public ErrorestExceptionData withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public ErrorestExceptionData addError(String code, String description) {
        errors.add(new Error(code, description));
        return this;
    }

    public HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public ErrorestExceptionData withResponseHttpStatus(HttpStatus responseHttpStatus) {
        this.responseHttpStatus = responseHttpStatus;
        return this;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public ErrorestExceptionData withLoggingLevel(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
        return this;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }

    public ErrorestExceptionData withLogStackTrace() {
        this.logStackTrace = true;
        return this;
    }

    public Throwable getCause() {
        return cause;
    }

    public ErrorestExceptionData withCause(Throwable cause) {
        if (cause != null) {
            logStackTrace = true;
        }
        this.cause = cause;
        return this;
    }
}
