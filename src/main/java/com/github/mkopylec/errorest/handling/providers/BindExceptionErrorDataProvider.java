package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.BeanValidationError;
import com.github.mkopylec.errorest.handling.ErrorData;
import com.github.mkopylec.errorest.handling.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.ErrorData.ErrorDataBuilder.newErrorData;

public class BindExceptionErrorDataProvider extends BeanValidationErrorDataProvider<BindException> {

    public BindExceptionErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(BindException ex, HttpServletRequest request) {
        return buildErrorData(ex)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(BindException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(BindException ex) {
        BeanValidationError validationError = errorestProperties.getBeanValidationError();
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(validationError.getLoggingLevel())
                .withResponseStatus(validationError.getResponseHttpStatus())
                .withThrowable(ex)
                .withLogStackTrace(validationError.isLogStackTrace());
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            Error error = createError(objectError);
            builder.addError(error);
        });
        return builder;
    }
}
