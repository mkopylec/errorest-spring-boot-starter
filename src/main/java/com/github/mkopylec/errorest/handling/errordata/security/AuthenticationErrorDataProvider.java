package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AuthenticationErrorDataProvider extends SecurityErrorDataProvider<AuthenticationException> {

    public AuthenticationErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    protected HttpStatus getResponseHttpStatus(AuthenticationException ex) {
        return UNAUTHORIZED;
    }

    @Override
    protected String getErrorDescription(String requestHeaders) {
        return "Authentication failed for request with headers: " + requestHeaders;
    }
}
