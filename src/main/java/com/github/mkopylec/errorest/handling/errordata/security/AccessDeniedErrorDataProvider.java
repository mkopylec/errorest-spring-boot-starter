package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessDeniedErrorDataProvider extends ErrorDataProvider<AccessDeniedException> {

    public static final String SECURITY_ERROR_CODE = "SECURITY_ERROR";

    public AccessDeniedErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(AccessDeniedException ex, HttpServletRequest request) {
        return buildErrorData(ex, request.getHeader(AUTHORIZATION))
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(AccessDeniedException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        String authorizationHeader = getAuthorizationHeader(requestAttributes);
        return buildErrorData(ex, authorizationHeader)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorData.ErrorDataBuilder buildErrorData(AccessDeniedException ex, String authorizationHeader) {
        LoggingLevel loggingLevel = errorestProperties.getHttpClientError().getLoggingLevel();
        return newErrorData()
                .withLoggingLevel(loggingLevel)
                .withResponseStatus(FORBIDDEN)
                .withThrowable(ex)
                .addError(new Error(SECURITY_ERROR_CODE, "Access denied for request header: '" + AUTHORIZATION + ": " + authorizationHeader + "'"));
    }
}
