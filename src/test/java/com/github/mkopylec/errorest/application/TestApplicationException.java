package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.exceptions.ApplicationException;
import com.github.mkopylec.errorest.exceptions.ApplicationExceptionConfiguration;

import static com.github.mkopylec.errorest.logging.LoggingLevel.INFO;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;

public class TestApplicationException extends ApplicationException {

    public TestApplicationException() {
        super(new ApplicationExceptionConfiguration()
                .withResponseHttpStatus(I_AM_A_TEAPOT)
                .withLoggingLevel(INFO)
                .withCause(new RuntimeException("Application error cause"))
                .addError("ERROR_CODE_1", "Error 1")
                .addError("ERROR_CODE_2", "Error 2")
        );
    }
}
