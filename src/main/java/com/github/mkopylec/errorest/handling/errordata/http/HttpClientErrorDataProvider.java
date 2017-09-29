package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
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
    public ErrorData getErrorData(T ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex, responseHttpStatus)
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorData getErrorData(T ex, HttpServletRequest request, HttpStatus responseHttpStatus) {
        return buildErrorData(ex, responseHttpStatus)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    protected ErrorDataBuilder buildErrorData(T ex, HttpStatus responseHttpStatus) {
        LoggingLevel loggingLevel = errorestProperties.getHttpClientError().getLoggingLevel();
        return newErrorData()
                .withLoggingLevel(loggingLevel)
                .withResponseStatus(responseHttpStatus)
                .withThrowable(ex)
                .addError(new Error(HTTP_CLIENT_ERROR_CODE, getErrorDescription(ex)));
    }

    protected abstract String getErrorDescription(T ex);
}
