package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL;
import static com.github.mkopylec.errorest.logging.LoggingLevel.WARN;

@ConfigurationProperties("errorest")
public class ErrorestProperties {

    private ResponseBodyFormat responseBodyFormat = FULL;
    private BeanValidationError beanValidationError = new BeanValidationError();
    private HttpClientError httpClientError = new HttpClientError();

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

    public enum ResponseBodyFormat {
        WITHOUT_DESCRIPTIONS, FULL
    }

    public static class BeanValidationError {

        private LoggingLevel loggingLevel = WARN;

        public LoggingLevel getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
        }
    }

    public static class HttpClientError {

        private LoggingLevel loggingLevel = WARN;

        public LoggingLevel getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
        }
    }
}
