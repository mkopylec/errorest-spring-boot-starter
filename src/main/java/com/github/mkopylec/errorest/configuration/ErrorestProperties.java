package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.LoggingLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.mkopylec.errorest.LoggingLevel.ERROR;

@ConfigurationProperties("errorest")
public class ErrorestProperties {

    private UnexpectedError unexpectedError = new UnexpectedError();

    public UnexpectedError getUnexpectedError() {
        return unexpectedError;
    }

    public void setUnexpectedError(UnexpectedError unexpectedError) {
        this.unexpectedError = unexpectedError;
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
