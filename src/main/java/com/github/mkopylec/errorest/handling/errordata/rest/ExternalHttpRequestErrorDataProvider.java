package com.github.mkopylec.errorest.handling.errordata.rest;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.logging.LoggingLevel.ERROR;

public class ExternalHttpRequestErrorDataProvider extends ErrorDataProvider<ExternalHttpRequestException> {

    public ExternalHttpRequestErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(ExternalHttpRequestException ex, HttpServletRequest request) {
        return buildErrorData(ex, ex.getStatusCode())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(ExternalHttpRequestException ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        String requestUri = getRequestUri(errorAttributes, webRequest);
        return buildErrorData(ex, ex.getStatusCode())
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(ExternalHttpRequestException ex, HttpStatus responseHttpStatus) {
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(getLoggingLevel(responseHttpStatus))
                .withResponseStatus(responseHttpStatus)
                .withThrowable(ex);
        ex.getResponseBodyAsErrors().getErrors().forEach(builder::addError);
        return builder;
    }

    protected LoggingLevel getLoggingLevel(HttpStatus responseHttpStatus) {
        return responseHttpStatus.is4xxClientError() ? errorestProperties.getHttpClientError().getLoggingLevel() : ERROR;
    }
}
