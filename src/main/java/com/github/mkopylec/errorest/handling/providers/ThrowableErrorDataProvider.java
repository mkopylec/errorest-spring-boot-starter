package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.UnexpectedError;
import com.github.mkopylec.errorest.handling.ErrorData;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.handling.RequestMethodAttributeSettingFilter.REQUEST_METHOD_ERROR_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class ThrowableErrorDataProvider implements ErrorDataProvider<Throwable> {

    protected final ErrorestProperties errorestProperties;

    public ThrowableErrorDataProvider(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    @Override
    public ErrorData getErrorData(Throwable ex, HttpServletRequest request) {
        UnexpectedError unexpectedError = errorestProperties.getUnexpectedError();
        return newErrorData()
                .withLoggingLevel(unexpectedError.getLoggingLevel())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .withResponseStatus(INTERNAL_SERVER_ERROR)
                .withThrowable(ex)
                .withLogStackTrace(unexpectedError.isLogStackTrace())
                .addError(new Error(unexpectedError.getCode(), ex.getMessage()))
                .build();
    }

    @Override
    public ErrorData getErrorData(Throwable ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = defaultIfBlank((String) requestAttributes.getAttribute(REQUEST_METHOD_ERROR_ATTRIBUTE, SCOPE_REQUEST), NOT_AVAILABLE_REQUEST_DATA);
        String requestUri = errorAttributes.getErrorAttributes(requestAttributes, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_REQUEST_DATA).toString();
        UnexpectedError unexpectedError = errorestProperties.getUnexpectedError();
        return newErrorData()
                .withLoggingLevel(unexpectedError.getLoggingLevel())
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(defaultResponseStatus)
                .withThrowable(ex)
                .withLogStackTrace(unexpectedError.isLogStackTrace())
                .addError(new Error(unexpectedError.getCode(), ex.getMessage()))
                .build();
    }
}
