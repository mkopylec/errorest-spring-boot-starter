package com.github.mkopylec.errorest.handling.errordata.validation;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * INFO: BindException will never be thrown when request body is annotated with @RequestBody.
 */
public class BindExceptionErrorDataProvider extends BeanValidationErrorDataProvider<BindException> {

    public BindExceptionErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(BindException ex, HttpServletRequest request) {
        return buildErrorData(ex.getBindingResult())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .withThrowable(ex)
                .build();
    }

    @Override
    public ErrorData getErrorData(BindException ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        String requestUri = getRequestUri(errorAttributes, webRequest);
        return buildErrorData(ex.getBindingResult())
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .withThrowable(ex)
                .build();
    }
}
