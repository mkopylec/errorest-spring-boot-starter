package com.github.mkopylec.errorest.logging;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.slf4j.Logger;

import static com.github.mkopylec.errorest.logging.LoggingLevel.DEBUG;
import static com.github.mkopylec.errorest.logging.LoggingLevel.ERROR;
import static com.github.mkopylec.errorest.logging.LoggingLevel.INFO;
import static com.github.mkopylec.errorest.logging.LoggingLevel.TRACE;
import static com.github.mkopylec.errorest.logging.LoggingLevel.WARN;
import static org.slf4j.LoggerFactory.getLogger;

public class ExceptionLogger {

    private static final Logger log = getLogger(ExceptionLogger.class);

    public void log(ErrorData errorData) {
        log(TRACE, errorData, () -> log.trace(createLogMessage(errorData), errorData.getThrowable()));
        log(DEBUG, errorData, () -> log.debug(createLogMessage(errorData), errorData.getThrowable()));
        log(INFO, errorData, () -> log.info(createLogMessage(errorData), errorData.getThrowable()));
        log(WARN, errorData, () -> log.warn(createLogMessage(errorData), errorData.getThrowable()));
        log(ERROR, errorData, () -> log.error(createLogMessage(errorData), errorData.getThrowable()));
    }

    protected void log(LoggingLevel loggingLevel, ErrorData errorData, Runnable logger) {
        if (!errorData.hasLoggingLevel(loggingLevel)) {
            return;
        }
        logger.run();
    }

    protected String createLogMessage(ErrorData errorData) {
        return "ID: " + errorData.getId() + " | " + errorData.getRequestMethod() + " " + errorData.getRequestUri() + " " + errorData.getResponseStatus() + " | " + errorData.getErrors();
    }
}
