package com.github.mkopylec.errorest.handlers;

import com.github.mkopylec.errorest.Error;
import com.github.mkopylec.errorest.ErrorData;
import com.github.mkopylec.errorest.Errors;
import com.github.mkopylec.errorest.ExceptionLogger;
import com.github.mkopylec.errorest.RestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.github.mkopylec.errorest.ErrorData.ErrorDataBuilder.newErrorData;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ControllerErrorHandler {

    protected final ExceptionLogger exceptionLogger;

    public ControllerErrorHandler(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Errors> handleRestException(RestException ex) {
        ErrorData errorData = newErrorData()
                .withLoggingLevel(loggingLevel)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(responseStatus)
                .withThrowable(throwable)
                .withLogStackTrace(logStackTrace)
                .addError(new Error(errorCode, errorDescription))
                .build();
        exceptionLogger.log(errorData);
        Errors errors = new Errors(errorData.getErrors());
        return status(errorData.getResponseStatus())
                .body(errors);
    }
}
