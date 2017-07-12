package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.ErrorData;
import org.springframework.web.HttpRequestMethodNotSupportedException;

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
    protected String getErrorDescription(HttpRequestMethodNotSupportedException ex) {
        return METHOD_NOT_ALLOWED.getReasonPhrase() + ", supported methods are " + join(ex.getSupportedMethods(), ", ");
    }
}
