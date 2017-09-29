package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MissingServletRequestPartErrorDataProvider extends HttpClientErrorDataProvider<MissingServletRequestPartException> {

    public MissingServletRequestPartErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(MissingServletRequestPartException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    public ErrorData getErrorData(MissingServletRequestPartException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        return super.getErrorData(ex, request, BAD_REQUEST, errorAttributes, requestAttributes);
    }

    @Override
    protected String getErrorDescription(MissingServletRequestPartException ex) {
        return BAD_REQUEST.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
