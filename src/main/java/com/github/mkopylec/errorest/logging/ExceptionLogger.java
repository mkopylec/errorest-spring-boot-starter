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
        log(TRACE, errorData,
                () -> log.trace(createFullLog(errorData), errorData.getThrowable()),
                () -> log.trace(createLogTemplate(errorData), errorData.getId(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(DEBUG, errorData,
                () -> log.debug(createFullLog(errorData), errorData.getThrowable()),
                () -> log.debug(createLogTemplate(errorData), errorData.getId(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(INFO, errorData,
                () -> log.info(createFullLog(errorData), errorData.getThrowable()),
                () -> log.info(createLogTemplate(errorData), errorData.getId(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(WARN, errorData,
                () -> log.warn(createFullLog(errorData), errorData.getThrowable()),
                () -> log.warn(createLogTemplate(errorData), errorData.getId(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
        );
        log(ERROR, errorData,
                () -> log.error(createFullLog(errorData), errorData.getThrowable()),
                () -> log.error(createLogTemplate(errorData), errorData.getId(), errorData.getRequestMethod(), errorData.getRequestUri(), errorData.getResponseStatus(), errorData.getErrors())
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

    protected String createLogTemplate(ErrorData errorData) {
        return errorData.hasMessage() ? "ID: {} | {} {} {} | " + errorData.getMessage() + " | {}" : "ID: {} | {} {} {} | {}";
    }

    protected String createFullLog(ErrorData errorData) {
        String log = "ID: " + errorData.getId() + " | " + errorData.getRequestMethod() + " " + errorData.getRequestUri() + " " + errorData.getResponseStatus();
        if (errorData.hasMessage()) {
            log += " | " + errorData.getMessage();
        }
        log += " | " + errorData.getErrors();
        if (errorData.getThrowable() == null) {
            log += " | No stack trace available";
        }
        return log;
    }
}
