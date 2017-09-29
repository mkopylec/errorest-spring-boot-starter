package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.join;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class MediaTypeNotAcceptableErrorDataProvider extends HttpClientErrorDataProvider<HttpMediaTypeNotAcceptableException> {

    public MediaTypeNotAcceptableErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        return getErrorData(ex, request, NOT_ACCEPTABLE);
    }

    @Override
    public ErrorData getErrorData(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        return super.getErrorData(ex, request, NOT_ACCEPTABLE, errorAttributes, requestAttributes);
    }

    @Override
    protected String getErrorDescription(HttpMediaTypeNotAcceptableException ex) {
        return NOT_ACCEPTABLE.getReasonPhrase() + ", acceptable media types are " + join(ex.getSupportedMediaTypes(), ", ");
    }
}
