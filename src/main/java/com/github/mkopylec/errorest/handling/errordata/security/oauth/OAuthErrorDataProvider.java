package com.github.mkopylec.errorest.handling.errordata.security.oauth;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.security.SecurityErrorDataProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import static org.springframework.http.HttpStatus.valueOf;

public class OAuthErrorDataProvider extends SecurityErrorDataProvider<OAuth2Exception> {

    public OAuthErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    protected HttpStatus getResponseHttpStatus(OAuth2Exception ex) {
        return valueOf(ex.getHttpErrorCode());
    }

    @Override
    protected String getErrorDescription(String requestHeaders) {
        return "OAuth authentication failed for request with headers: " + requestHeaders;
    }
}
