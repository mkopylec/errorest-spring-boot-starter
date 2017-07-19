package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

public class ServletRequestBindingErrorDataProvider extends HttpClientErrorDataProvider<ServletRequestBindingException> {

    public ServletRequestBindingErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(ServletRequestBindingException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    protected String getErrorDescription(ServletRequestBindingException ex) {
        return BAD_REQUEST.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
