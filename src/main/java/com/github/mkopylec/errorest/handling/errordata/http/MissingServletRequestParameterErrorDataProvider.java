package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MissingServletRequestParameterErrorDataProvider extends HttpClientErrorDataProvider<MissingServletRequestParameterException> {

    public MissingServletRequestParameterErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    public ErrorData getErrorData(MissingServletRequestParameterException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        return super.getErrorData(ex, request, BAD_REQUEST, errorAttributes, webRequest);
    }

    @Override
    protected String getErrorDescription(MissingServletRequestParameterException ex) {
        return BAD_REQUEST.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
