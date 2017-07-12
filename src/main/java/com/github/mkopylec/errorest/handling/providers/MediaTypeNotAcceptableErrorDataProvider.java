package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.ErrorData;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

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
    protected String getErrorDescription(HttpMediaTypeNotAcceptableException ex) {
        return NOT_ACCEPTABLE.getReasonPhrase() + ", acceptable media types are " + join(ex.getSupportedMediaTypes(), ", ");
    }
}
