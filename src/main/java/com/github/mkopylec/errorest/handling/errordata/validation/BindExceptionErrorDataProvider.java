package com.github.mkopylec.errorest.handling.errordata.validation;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestAttributes;

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
    public ErrorData getErrorData(BindException ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex.getBindingResult())
                .withRequestMethod(request.getMethod())
                .withRequestUri(requestUri)
                .withThrowable(ex)
                .build();
    }
}
