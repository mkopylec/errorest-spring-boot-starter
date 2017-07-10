package com.github.mkopylec.errorest;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ExceptionLogger {

    private static final Logger log = getLogger(ExceptionLogger.class);

    protected void log(ErrorData errorData) {
        switch (errorData.getLogLevel()) {
            case TRACE:
                if (errorData.isLogStackTrace()) {
                    log.trace(createFullLog(errorData), ex);
                } else {

                }
        }
    }

    protected String createLogTemplate() {
        return "{} {} {} | {} | {}";
    }

    protected String createFullLog(ErrorData errorData) {
        return errorData.getRequestMethod() + " " + errorData.getRequestUri() + " " + errorData.getResponseStatus() +
                " | " + errorData.getErrorCode() + " | " + errorData.getErrorDescription();
    }
}
