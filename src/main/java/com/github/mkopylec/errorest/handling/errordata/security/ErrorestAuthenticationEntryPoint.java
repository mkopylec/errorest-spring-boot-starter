package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.response.Errors;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    protected final ErrorDataProviderContext providerContext;
    protected final ErrorsFactory errorsFactory;
    protected final ErrorsHttpResponseSetter responseBodySetter;

    public ErrorestAuthenticationEntryPoint(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory, ErrorsHttpResponseSetter responseBodySetter) {
        this.providerContext = providerContext;
        this.errorsFactory = errorsFactory;
        this.responseBodySetter = responseBodySetter;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        ErrorData errorData = getErrorData(ex, request);
        Errors errors = errorsFactory.logAndCreateErrors(errorData);
        responseBodySetter.setErrorsResponse(errors, errorData.getResponseStatus(), request, response);
    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(AuthenticationException ex, HttpServletRequest request) {
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return ((SecurityErrorDataProvider) provider).createErrorData(ex, request);
    }
}
