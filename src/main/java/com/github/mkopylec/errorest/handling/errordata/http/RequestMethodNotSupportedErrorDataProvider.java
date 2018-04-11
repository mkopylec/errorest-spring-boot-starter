package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.join;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

public class RequestMethodNotSupportedErrorDataProvider extends HttpClientErrorDataProvider<HttpRequestMethodNotSupportedException> {

    public RequestMethodNotSupportedErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return getErrorData(ex, request, METHOD_NOT_ALLOWED);
    }

    @Override
    public ErrorData getErrorData(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        return super.getErrorData(ex, request, METHOD_NOT_ALLOWED, errorAttributes, webRequest);
    }

    @Override
    protected String getErrorDescription(HttpRequestMethodNotSupportedException ex) {
        return METHOD_NOT_ALLOWED.getReasonPhrase() + ", supported methods are " + join(ex.getSupportedMethods(), ", ");
    }
}
