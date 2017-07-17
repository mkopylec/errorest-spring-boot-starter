package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.RestApplicationException;
import com.github.mkopylec.errorest.exceptions.RestResponseException;
import com.github.mkopylec.errorest.handling.errordata.http.MediaTypeNotAcceptableErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MediaTypeNotSupportedErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MissingServletRequestParameterErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.RequestMethodNotSupportedErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.rest.RestApplicationExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.rest.RestResponseExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.validation.BindExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.validation.MethodArgumentNotValidErrorDataProvider;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

public class ErrorDataProviderContext {

    protected final ErrorestProperties errorestProperties;

    public ErrorDataProviderContext(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof RestApplicationException) {
            return new RestApplicationExceptionErrorDataProvider(errorestProperties);
        }
        if (ex instanceof RestResponseException) {
            return new RestResponseExceptionErrorDataProvider(errorestProperties);
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
        if (ex instanceof MissingServletRequestParameterException) {
            return new MissingServletRequestParameterErrorDataProvider(errorestProperties);
        }
        return new ThrowableErrorDataProvider(errorestProperties);
    }
}
