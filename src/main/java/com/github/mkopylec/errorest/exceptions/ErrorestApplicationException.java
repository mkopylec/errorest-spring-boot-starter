package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

public abstract class ErrorestApplicationException extends RuntimeException {

    protected final List<Error> errors;
    protected final HttpStatus responseHttpStatus;
    protected final LoggingLevel loggingLevel;
    protected final boolean logStackTrace;

    public ErrorestApplicationException(ErrorestExceptionData data) {
        this(data.getMessage(), data.getErrors(), data.getResponseHttpStatus(), data.getLoggingLevel(), data.isLogStackTrace(), data.getCause());
    }

    public ErrorestApplicationException(String message, ErrorestResponseException cause, LoggingLevel loggingLevel) {
        this(message, cause.getResponseBodyAsErrors().getErrors(), cause.getStatusCode(), loggingLevel, true, cause);
    }

    private ErrorestApplicationException(String message, List<Error> errors, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, boolean logStackTrace, Throwable cause) {
        super(message, cause);
        notEmpty(errors, "Empty errors");
        errors.forEach(error -> {
            notNull(error, "Empty error");
            hasText(error.getCode(), "Empty error code");
            hasText(error.getDescription(), "Empty error description");
        });
        notNull(responseHttpStatus, "Empty response HTTP status");
        notNull(loggingLevel, "Empty logging level");
        this.errors = errors;
        this.responseHttpStatus = responseHttpStatus;
        this.loggingLevel = loggingLevel;
        this.logStackTrace = logStackTrace;
    }

    public List<Error> getErrors() {
        return errors;
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
