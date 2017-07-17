package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.HttpClientError;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;

public abstract class HttpClientErrorDataProvider<T extends Throwable> extends ErrorDataProvider<T> {

    public static final String HTTP_CLIENT_ERROR_CODE = "HTTP_CLIENT_ERROR";

    public HttpClientErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(T ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex, defaultResponseStatus)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(defaultResponseStatus)
                .build();
    }

    protected ErrorData getErrorData(T ex, HttpServletRequest request, HttpStatus responseHttpStatus) {
        return buildErrorData(ex, responseHttpStatus)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    protected ErrorDataBuilder buildErrorData(T ex, HttpStatus responseHttpStatus) {
        HttpClientError httpClientError = errorestProperties.getHttpClientError();
        return newErrorData()
                .withLoggingLevel(httpClientError.getLoggingLevel())
                .withResponseStatus(responseHttpStatus)
                .withThrowable(ex)
                .withLogStackTrace(httpClientError.isLogStackTrace())
                .addError(new Error(HTTP_CLIENT_ERROR_CODE, getErrorDescription(ex)));
    }

    protected abstract String getErrorDescription(T ex);
}
