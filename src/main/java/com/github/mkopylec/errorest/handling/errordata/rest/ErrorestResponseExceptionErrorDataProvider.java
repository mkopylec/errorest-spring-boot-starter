package com.github.mkopylec.errorest.handling.errordata.rest;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.ErrorestResponseException;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.EXTERNAL_REQUEST_FAIL_MESSAGE;
import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.logging.LoggingLevel.ERROR;

public class ErrorestResponseExceptionErrorDataProvider extends ErrorDataProvider<ErrorestResponseException> {

    public ErrorestResponseExceptionErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(ErrorestResponseException ex, HttpServletRequest request) {
        return buildErrorData(ex, ex.getStatusCode())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(ErrorestResponseException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex, defaultResponseStatus)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(defaultResponseStatus)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(ErrorestResponseException ex, HttpStatus responseHttpStatus) {
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(getLoggingLevel(responseHttpStatus))
                .withResponseStatus(responseHttpStatus)
                .withMessage(EXTERNAL_REQUEST_FAIL_MESSAGE)
                .withThrowable(ex)
                .withLogStackTrace(true);
        ex.getResponseBodyAsErrors().getErrors().forEach(builder::addError);
        return builder;
    }

    protected LoggingLevel getLoggingLevel(HttpStatus responseHttpStatus) {
        return responseHttpStatus.is4xxClientError() ? errorestProperties.getHttpClientError().getLoggingLevel() : ERROR;
    }
}
