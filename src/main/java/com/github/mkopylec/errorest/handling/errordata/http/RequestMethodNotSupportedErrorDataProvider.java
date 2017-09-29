package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.RequestAttributes;

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
    public ErrorData getErrorData(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        return super.getErrorData(ex, request, METHOD_NOT_ALLOWED, errorAttributes, requestAttributes);
    }

    @Override
    protected String getErrorDescription(HttpRequestMethodNotSupportedException ex) {
        return METHOD_NOT_ALLOWED.getReasonPhrase() + ", supported methods are " + join(ex.getSupportedMethods(), ", ");
    }
}
