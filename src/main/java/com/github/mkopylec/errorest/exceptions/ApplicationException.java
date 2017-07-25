package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

public abstract class ApplicationException extends RuntimeException {

    protected final List<Error> errors;
    protected final HttpStatus responseHttpStatus;
    protected final LoggingLevel loggingLevel;

    public ApplicationException(ApplicationExceptionConfiguration configuration) {
        this(configuration.getErrors(), configuration.getResponseHttpStatus(), configuration.getLoggingLevel(), configuration.getCause());
    }

    private ApplicationException(List<Error> errors, HttpStatus responseHttpStatus, LoggingLevel loggingLevel, Throwable cause) {
        super(cause);
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

    @Override
    public String getMessage() {
        return "An application error has occurred. Status: " + responseHttpStatus + " | " + errors;
    }
}
