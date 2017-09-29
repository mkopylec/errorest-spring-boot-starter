package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NoHandlerFoundErrorDataProvider extends HttpClientErrorDataProvider<NoHandlerFoundException> {

    public NoHandlerFoundErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(NoHandlerFoundException ex, HttpServletRequest request) {
        return getErrorData(ex, request, NOT_FOUND);
    }

    @Override
    public ErrorData getErrorData(NoHandlerFoundException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        return super.getErrorData(ex, request, NOT_FOUND, errorAttributes, requestAttributes);
    }

    @Override
    protected String getErrorDescription(NoHandlerFoundException ex) {
        return NOT_FOUND.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
