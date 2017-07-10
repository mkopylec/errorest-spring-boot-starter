package com.github.mkopylec.errorest.handlers;

import com.github.mkopylec.errorest.RestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity handleRestException(RestException ex) {
        return null;
    }
}
