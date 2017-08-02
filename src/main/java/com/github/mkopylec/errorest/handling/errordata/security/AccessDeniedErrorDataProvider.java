package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessDeniedErrorDataProvider extends SecurityErrorDataProvider<AccessDeniedException> {

    public AccessDeniedErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    protected HttpStatus getResponseHttpStatus() {
        return FORBIDDEN;
    }
}
