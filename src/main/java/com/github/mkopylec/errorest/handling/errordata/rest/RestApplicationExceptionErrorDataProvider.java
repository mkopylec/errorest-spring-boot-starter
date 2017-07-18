package com.github.mkopylec.errorest.handling.errordata.rest;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.RestApplicationException;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;

public class RestApplicationExceptionErrorDataProvider extends ErrorDataProvider<RestApplicationException> {

    public RestApplicationExceptionErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(RestApplicationException ex, HttpServletRequest request) {
        return buildErrorData(ex)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(RestApplicationException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(RestApplicationException ex) {
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withResponseStatus(ex.getResponseHttpStatus())
                .withMessage(ex.getMessage())
                .withThrowable(ex)
                .withLogStackTrace(ex.isLogStackTrace());
        ex.getErrors().forEach(builder::addError);
        return builder;
    }
}
