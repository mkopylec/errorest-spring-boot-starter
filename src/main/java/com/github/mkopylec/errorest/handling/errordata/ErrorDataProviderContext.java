package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.ApplicationException;
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException;
import com.github.mkopylec.errorest.handling.errordata.generic.ThrowableErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MediaTypeNotAcceptableErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MediaTypeNotSupportedErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MessageNotReadableErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MissingServletRequestParameterErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.MissingServletRequestPartErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.NoHandlerFoundErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.RequestMethodNotSupportedErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.ServletRequestBindingErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.http.TypeMismatchErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.rest.ApplicationExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.rest.ExternalHttpRequestExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.validation.BindExceptionErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.validation.MethodArgumentNotValidErrorDataProvider;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

public class ErrorDataProviderContext {

    protected final ErrorestProperties errorestProperties;

    public ErrorDataProviderContext(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof ApplicationException) {
            return new ApplicationExceptionErrorDataProvider(errorestProperties);
        }
        if (ex instanceof ExternalHttpRequestException) {
            return new ExternalHttpRequestExceptionErrorDataProvider(errorestProperties);
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
        if (ex instanceof HttpMessageNotReadableException) {
            return new MessageNotReadableErrorDataProvider(errorestProperties);
        }
        if (ex instanceof MissingServletRequestPartException) {
            return new MissingServletRequestPartErrorDataProvider(errorestProperties);
        }
        if (ex instanceof NoHandlerFoundException) {
            return new NoHandlerFoundErrorDataProvider(errorestProperties);
        }
        if (ex instanceof ServletRequestBindingException) {
            return new ServletRequestBindingErrorDataProvider(errorestProperties);
        }
        if (ex instanceof TypeMismatchException) {
            return new TypeMismatchErrorDataProvider(errorestProperties);
        }
        return new ThrowableErrorDataProvider(errorestProperties);
    }
}
