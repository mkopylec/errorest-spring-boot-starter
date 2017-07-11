package com.github.mkopylec.errorest;

import org.slf4j.Logger;

import static com.github.mkopylec.errorest.LoggingLevel.DEBUG;
import static com.github.mkopylec.errorest.LoggingLevel.ERROR;
import static com.github.mkopylec.errorest.LoggingLevel.INFO;
import static com.github.mkopylec.errorest.LoggingLevel.TRACE;
import static com.github.mkopylec.errorest.LoggingLevel.WARN;
import static org.slf4j.LoggerFactory.getLogger;

public class ExceptionLogger {

    private static final Logger log = getLogger(ExceptionLogger.class);

    public void log(ErrorData errorData) {
        log(TRACE, errorData,
                () -> log.trace(createFullLog(errorData), errorData.getThrowable()),
                () -> log.trace(createLogTemplate(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(DEBUG, errorData,
                () -> log.debug(createFullLog(errorData), errorData.getThrowable()),
                () -> log.debug(createLogTemplate(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(INFO, errorData,
                () -> log.info(createFullLog(errorData), errorData.getThrowable()),
                () -> log.info(createLogTemplate(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(WARN, errorData,
                () -> log.warn(createFullLog(errorData), errorData.getThrowable()),
                () -> log.warn(createLogTemplate(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(ERROR, errorData,
                () -> log.error(createFullLog(errorData), errorData.getThrowable()),
                () -> log.error(createLogTemplate(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
    }

    protected void log(LoggingLevel loggingLevel, ErrorData errorData, Runnable withStackTraceLogger, Runnable withoutStackTraceLogger) {
        if (errorData.getLoggingLevel() != loggingLevel) {
            return;
        }
        if (errorData.isLogStackTrace()) {
            withStackTraceLogger.run();
        } else {
            withoutStackTraceLogger.run();
        }
    }

    protected String createLogTemplate() {
        return "{} {} {} {}";
    }

    protected String createFullLog(ErrorData errorData) {
        return errorData.getRequestMethod() + " " + errorData.getRequestUri() + " " + errorData.getResponseStatus() + errorData.getErrors();
    }
}
