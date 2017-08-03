package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApplicationExceptionConfiguration {

    protected List<Error> errors = new ErrorsLoggingList();
    protected HttpStatus responseHttpStatus;
    protected LoggingLevel loggingLevel;
    protected Throwable cause;

    protected List<Error> getErrors() {
        return errors;
    }

    public ApplicationExceptionConfiguration addError(String code, String description) {
        errors.add(new Error(code, description));
        return this;
    }

    public ApplicationExceptionConfiguration withErrors(ErrorsLoggingList errors) {
        this.errors = errors;
        return this;
    }

    protected HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public ApplicationExceptionConfiguration withResponseHttpStatus(HttpStatus responseHttpStatus) {
        this.responseHttpStatus = responseHttpStatus;
        return this;
    }

    protected LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public ApplicationExceptionConfiguration withLoggingLevel(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
        return this;
    }

    protected Throwable getCause() {
        return cause;
    }

    public ApplicationExceptionConfiguration withCause(Throwable cause) {
        this.cause = cause;
        return this;
    }
}
