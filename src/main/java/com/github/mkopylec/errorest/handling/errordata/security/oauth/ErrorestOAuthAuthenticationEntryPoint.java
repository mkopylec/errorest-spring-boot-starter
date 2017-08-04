package com.github.mkopylec.errorest.handling.errordata.security.oauth;

import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

public class ErrorestOAuthAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    public ErrorestOAuthAuthenticationEntryPoint(OAuthExceptionRenderer  exceptionRenderer) {
        setExceptionRenderer(exceptionRenderer);
    }
}
