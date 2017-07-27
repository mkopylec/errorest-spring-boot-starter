package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.security.access.AccessDeniedException;

public class AuthenticationErrorDataProvider extends SecurityErrorDataProvider<AccessDeniedException> {

    public AuthenticationErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }
}
