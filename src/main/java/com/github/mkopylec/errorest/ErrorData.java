package com.github.mkopylec.errorest;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class ErrorData {

    protected final LogLevel logLevel;
    protected final String requestMethod;
    protected final String requestUri;
    protected final HttpStatus responseStatus;
    protected final String errorCode;
    protected final String errorDescription;
    protected final boolean logStackTrace;

    protected ErrorData(LogLevel logLevel, String requestMethod, String requestUri, HttpStatus responseStatus, String errorCode, String errorDescription, boolean logStackTrace) {
        this.logLevel = logLevel;
        this.requestMethod = requestMethod;
        this.requestUri = requestUri;
        this.responseStatus = responseStatus;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.logStackTrace = logStackTrace;
    }

    public LogLevel getLogLevel() {
        return logLevel;
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

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }

    public static final class ErrorDataBuilder {

        protected LogLevel logLevel;
        protected String requestMethod;
        protected String requestUri;
        protected HttpStatus responseStatus;
        protected String errorCode;
        protected String errorDescription;
        protected boolean logStackTrace;

        private ErrorDataBuilder() {
        }

        public static ErrorDataBuilder newErrorData() {
            return new ErrorDataBuilder();
        }

        public ErrorDataBuilder withLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
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

        public ErrorDataBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDataBuilder withErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
            return this;
        }

        public ErrorDataBuilder withLogStackTrace(boolean logStackTrace) {
            this.logStackTrace = logStackTrace;
            return this;
        }

        public ErrorData build() {
            return new ErrorData(logLevel, requestMethod, requestUri, responseStatus, errorCode, errorDescription, logStackTrace);
        }
    }
}
