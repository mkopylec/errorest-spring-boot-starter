package com.github.mkopylec.errorest.handling.errordata.security.oauth;

import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

public class ErrorestOAuthAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    public ErrorestOAuthAccessDeniedHandler(OAuthExceptionRenderer exceptionRenderer) {
        setExceptionRenderer(exceptionRenderer);
    }
}
