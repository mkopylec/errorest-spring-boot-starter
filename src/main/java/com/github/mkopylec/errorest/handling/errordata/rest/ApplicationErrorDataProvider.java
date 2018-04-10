package com.github.mkopylec.errorest.handling.errordata.rest;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.ApplicationException;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;

public class ApplicationErrorDataProvider extends ErrorDataProvider<ApplicationException> {

    public ApplicationErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(ApplicationException ex, HttpServletRequest request) {
        return buildErrorData(ex)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(ApplicationException ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        String requestUri = getRequestUri(errorAttributes, webRequest);
        return buildErrorData(ex)
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(ApplicationException ex) {
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withResponseStatus(ex.getResponseHttpStatus())
                .withThrowable(ex);
        ex.getErrors().forEach(builder::addError);
        return builder;
    }
}
