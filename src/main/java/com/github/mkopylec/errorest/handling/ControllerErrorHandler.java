package com.github.mkopylec.errorest.handling;

import com.github.mkopylec.errorest.exceptions.RestException;
import com.github.mkopylec.errorest.logging.ExceptionLogger;
import com.github.mkopylec.errorest.response.Error;
import com.github.mkopylec.errorest.response.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.ErrorData.ErrorDataBuilder.newErrorData;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ControllerErrorHandler {

    protected final ExceptionLogger exceptionLogger;

    public ControllerErrorHandler(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Errors> handleRestException(RestException ex, HttpServletRequest request) {
        ErrorData errorData = newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .withResponseStatus(ex.getResponseHttpStatus())
                .withThrowable(ex)
                .withLogStackTrace(ex.isLogStackTrace())
                .addError(new Error(ex.getErrorCode(), ex.getErrorDescription()))
                .build();
        return logAndCreateResponse(errorData);
    }

    protected ResponseEntity<Errors> logAndCreateResponse(ErrorData errorData) {
        exceptionLogger.log(errorData);
        Errors errors = new Errors(errorData.getErrors());
        return status(errorData.getResponseStatus())
                .body(errors);
    }
}
