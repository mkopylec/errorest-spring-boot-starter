package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL;
import static com.github.mkopylec.errorest.logging.LoggingLevel.ERROR;
import static com.github.mkopylec.errorest.logging.LoggingLevel.WARN;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ConfigurationProperties("errorest")
public class ErrorestProperties {

    // TODO response body format support
    private ResponseBodyFormat responseBodyFormat = FULL;
    private BeanValidationError beanValidationError = new BeanValidationError();
    private HttpClientError httpClientError = new HttpClientError();
    private UnexpectedError unexpectedError = new UnexpectedError();

    public ResponseBodyFormat getResponseBodyFormat() {
        return responseBodyFormat;
    }

    public void setResponseBodyFormat(ResponseBodyFormat responseBodyFormat) {
        this.responseBodyFormat = responseBodyFormat;
    }

    public BeanValidationError getBeanValidationError() {
        return beanValidationError;
    }

    public void setBeanValidationError(BeanValidationError beanValidationError) {
        this.beanValidationError = beanValidationError;
    }

    public HttpClientError getHttpClientError() {
        return httpClientError;
    }

    public void setHttpClientError(HttpClientError httpClientError) {
        this.httpClientError = httpClientError;
    }

    public UnexpectedError getUnexpectedError() {
        return unexpectedError;
    }

    public void setUnexpectedError(UnexpectedError unexpectedError) {
        this.unexpectedError = unexpectedError;
    }

    public enum ResponseBodyFormat {
        WITHOUT_DESCRIPTIONS, FULL
    }

    public static class BeanValidationError {

        private LoggingLevel loggingLevel = WARN;
        private boolean logStackTrace = false;
        private HttpStatus responseHttpStatus = UNPROCESSABLE_ENTITY;

        public LoggingLevel getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        public boolean isLogStackTrace() {
            return logStackTrace;
        }

        public void setLogStackTrace(boolean logStackTrace) {
            this.logStackTrace = logStackTrace;
        }

        public HttpStatus getResponseHttpStatus() {
            return responseHttpStatus;
        }

        public void setResponseHttpStatus(HttpStatus responseHttpStatus) {
            this.responseHttpStatus = responseHttpStatus;
        }
    }

    public static class HttpClientError {

        private String code = "HTTP_ERROR";
        private LoggingLevel loggingLevel = WARN;
        private boolean logStackTrace = false;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public LoggingLevel getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        public boolean isLogStackTrace() {
            return logStackTrace;
        }

        public void setLogStackTrace(boolean logStackTrace) {
            this.logStackTrace = logStackTrace;
        }
    }

    public static class UnexpectedError {

        private String code = "UNEXPECTED_ERROR";
        private LoggingLevel loggingLevel = ERROR;
        private boolean logStackTrace = true;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public LoggingLevel getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        public boolean isLogStackTrace() {
            return logStackTrace;
        }

        public void setLogStackTrace(boolean logStackTrace) {
            this.logStackTrace = logStackTrace;
        }
    }
}
