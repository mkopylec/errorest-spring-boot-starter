package com.github.mkopylec.errorest.response;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class OAuthErrors extends OAuth2Exception {

    protected final Errors errors;

    public OAuthErrors(Errors errors) {
        super(null);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
