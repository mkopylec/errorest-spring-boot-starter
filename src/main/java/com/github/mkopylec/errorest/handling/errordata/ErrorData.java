package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class ErrorData {

    public static final int ERRORS_ID_LENGTH = 10;

    protected final String id;
    protected final LoggingLevel loggingLevel;
    protected final String requestMethod;
    protected final String requestUri;
    protected final HttpStatus responseStatus;
    protected final List<Error> errors;
    protected final Throwable throwable;

    protected ErrorData(String id, LoggingLevel loggingLevel, String requestMethod, String requestUri, HttpStatus responseStatus, List<Error> errors, Throwable throwable) {
        this.id = id;
        this.loggingLevel = loggingLevel;
        this.requestMethod = requestMethod;
        this.requestUri = requestUri;
        this.responseStatus = responseStatus;
        this.errors = errors;
        this.throwable = throwable;
    }

    public String getId() {
        return id;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public boolean hasLoggingLevel(LoggingLevel level) {
        return loggingLevel == level;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public static final class ErrorDataBuilder {

        protected String id;
        protected LoggingLevel loggingLevel;
        protected String requestMethod;
        protected String requestUri;
        protected HttpStatus responseStatus;
        protected List<Error> errors = new ErrorsLoggingList();
        protected Throwable throwable;

        private ErrorDataBuilder() {
            id = randomAlphanumeric(ERRORS_ID_LENGTH).toLowerCase();
        }

        public static ErrorDataBuilder newErrorData() {
            return new ErrorDataBuilder();
        }

        public ErrorDataBuilder withLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
            return this;
        }

        public ErrorDataBuilder withRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public ErrorDataBuilder withRequestUri(String requestUri) {
            this.requestUri = requestUri;
            return this;
        }

        public ErrorDataBuilder withResponseStatus(HttpStatus responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public ErrorDataBuilder addError(Error error) {
            errors.add(error);
            return this;
        }

        public ErrorDataBuilder withThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public ErrorData build() {
            return new ErrorData(id, loggingLevel, requestMethod, requestUri, responseStatus, errors, throwable);
        }
    }
}
