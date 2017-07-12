package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.RestException;
import com.github.mkopylec.errorest.handling.providers.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.providers.ErrorData.ErrorDataBuilder.newErrorData;

public class RestExceptionErrorDataProvider extends ErrorDataProvider<RestException> {

    public RestExceptionErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(RestException ex, HttpServletRequest request) {
        return buildErrorData(ex)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(RestException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(RestException ex) {
        return newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withResponseStatus(ex.getResponseHttpStatus())
                .withThrowable(ex)
                .withLogStackTrace(ex.isLogStackTrace())
                .addError(new Error(ex.getErrorCode(), ex.getErrorDescription()));
    }
}
