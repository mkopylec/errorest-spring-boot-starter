package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class TypeMismatchErrorDataProvider extends HttpClientErrorDataProvider<TypeMismatchException> {

    public TypeMismatchErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(TypeMismatchException ex, HttpServletRequest request) {
        return getErrorData(ex, request, BAD_REQUEST);
    }

    @Override
    protected String getErrorDescription(TypeMismatchException ex) {
        return BAD_REQUEST.getReasonPhrase() + ", " + uncapitalize(ex.getMessage());
    }
}
