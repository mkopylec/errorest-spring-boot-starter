package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.security.oauth.OAuthErrorDataProvider;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class OAuthSecurityErrorDataProviderContext extends SecurityErrorDataProviderContext {

    public OAuthSecurityErrorDataProviderContext(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof OAuth2Exception) {
            return new OAuthErrorDataProvider(errorestProperties);
        }
        return super.getErrorDataProvider(ex);
    }
}
