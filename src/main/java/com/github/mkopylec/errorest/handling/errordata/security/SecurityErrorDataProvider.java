package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.ControllerErrorHandler.CONTROLLER_ERROR_HANDLING_ATTRIBUTE;
import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.handling.utils.HttpUtils.getHeadersAsText;

public abstract class SecurityErrorDataProvider<T extends Throwable> extends ErrorDataProvider<T> {

    public static final String SECURITY_ERROR_CODE = "SECURITY_ERROR";

    public SecurityErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(T ex, HttpServletRequest request) {
        if (request.getAttribute(CONTROLLER_ERROR_HANDLING_ATTRIBUTE) != null) {
            request.removeAttribute(CONTROLLER_ERROR_HANDLING_ATTRIBUTE);
            throw (RuntimeException) ex;
        }
        return buildErrorData(ex, getHeadersAsText(request))
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(T ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        String requestUri = getRequestUri(errorAttributes, webRequest);
        String requestHeaders = getRequestHeaders(webRequest);
        return buildErrorData(ex, requestHeaders)
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(T ex, String requestHeaders) {
        LoggingLevel loggingLevel = errorestProperties.getHttpClientError().getLoggingLevel();
        String description = getErrorDescription(requestHeaders);
        return newErrorData()
                .withLoggingLevel(loggingLevel)
                .withResponseStatus(getResponseHttpStatus())
                .withThrowable(ex)
                .addError(new Error(SECURITY_ERROR_CODE, description));
    }

    protected abstract HttpStatus getResponseHttpStatus();

    protected abstract String getErrorDescription(String requestHeaders);
}
