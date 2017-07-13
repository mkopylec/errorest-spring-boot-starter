package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.RestApplicationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ErrorDataProviderContext {

    protected final ErrorestProperties errorestProperties;

    public ErrorDataProviderContext(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof RestApplicationException) {
            return new RestApplicationExceptionErrorDataProvider(errorestProperties);
        }
        if (ex instanceof BindException) {
            return new BindExceptionErrorDataProvider(errorestProperties);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return new MethodArgumentNotValidErrorDataProvider(errorestProperties);
        }
        if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return new MediaTypeNotAcceptableErrorDataProvider(errorestProperties);
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return new MediaTypeNotSupportedErrorDataProvider(errorestProperties);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new RequestMethodNotSupportedErrorDataProvider(errorestProperties);
        }
        return new ThrowableErrorDataProvider(errorestProperties);
    }
}
