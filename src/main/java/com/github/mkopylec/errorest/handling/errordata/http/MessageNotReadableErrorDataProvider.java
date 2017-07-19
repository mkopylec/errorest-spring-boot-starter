package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MessageNotReadableErrorDataProvider extends HttpClientErrorDataProvider<HttpMessageNotReadableException> {

    public MessageNotReadableErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    protected String getErrorDescription(HttpMessageNotReadableException ex) {
        return BAD_REQUEST.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
