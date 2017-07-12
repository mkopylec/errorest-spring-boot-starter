package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class MethodArgumentNotValidErrorDataProvider extends BeanValidationErrorDataProvider<MethodArgumentNotValidException> {

    public MethodArgumentNotValidErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return buildErrorData(ex.getBindingResult())
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .withThrowable(ex)
                .build();
    }

    @Override
    public ErrorData getErrorData(MethodArgumentNotValidException ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex.getBindingResult())
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withThrowable(ex)
                .build();
    }
}
