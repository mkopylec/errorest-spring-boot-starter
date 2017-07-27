package com.github.mkopylec.errorest.handling.errordata.security;

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
import static com.github.mkopylec.errorest.handling.utils.HttpUtils.getHeadersAsText;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public abstract class SecurityErrorDataProvider<T extends Throwable> extends ErrorDataProvider<T> {

    public static final String SECURITY_ERROR_CODE = "SECURITY_ERROR";

    public SecurityErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(T ex, HttpServletRequest request) {
        return buildErrorData(ex, getHeadersAsText(request))
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(T ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        String requestHeaders = getRequestHeaders(requestAttributes);
        return buildErrorData(ex, requestHeaders)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(T ex, String requestHeaders) {
        LoggingLevel loggingLevel = errorestProperties.getHttpClientError().getLoggingLevel();
        return newErrorData()
                .withLoggingLevel(loggingLevel)
                .withResponseStatus(FORBIDDEN)
                .withThrowable(ex)
                .addError(new Error(SECURITY_ERROR_CODE, "Access denied for request headers: " + requestHeaders));
    }
}
