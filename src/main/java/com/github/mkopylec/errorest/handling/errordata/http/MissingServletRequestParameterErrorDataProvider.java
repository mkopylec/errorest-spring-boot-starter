package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

public class MissingServletRequestParameterErrorDataProvider extends HttpClientErrorDataProvider<MissingServletRequestParameterException> {

    public MissingServletRequestParameterErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    protected String getErrorDescription(MissingServletRequestParameterException ex) {
        return METHOD_NOT_ALLOWED.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
