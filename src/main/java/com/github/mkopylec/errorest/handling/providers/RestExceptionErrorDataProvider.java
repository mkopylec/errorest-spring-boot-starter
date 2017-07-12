package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.exceptions.RestException;
import com.github.mkopylec.errorest.handling.ErrorData;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.handling.RequestMethodAttributeSettingFilter.REQUEST_METHOD_ERROR_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class RestExceptionErrorDataProvider implements ErrorDataProvider<RestException> {

    @Override
    public ErrorData getErrorData(RestException ex, HttpServletRequest request) {
        return newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .withResponseStatus(ex.getResponseHttpStatus())
                .withThrowable(ex)
                .withLogStackTrace(ex.isLogStackTrace())
                .addError(new Error(ex.getErrorCode(), ex.getErrorDescription()))
                .build();
    }

    @Override
    public ErrorData getErrorData(RestException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = defaultIfBlank((String) requestAttributes.getAttribute(REQUEST_METHOD_ERROR_ATTRIBUTE, SCOPE_REQUEST), NOT_AVAILABLE_REQUEST_DATA);
        String requestUri = errorAttributes.getErrorAttributes(requestAttributes, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_REQUEST_DATA).toString();
        return newErrorData()
                .withLoggingLevel(ex.getLoggingLevel())
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(ex.getResponseHttpStatus())
                .withThrowable(ex)
                .withLogStackTrace(ex.isLogStackTrace())
                .addError(new Error(ex.getErrorCode(), ex.getErrorDescription()))
                .build();
    }
}
