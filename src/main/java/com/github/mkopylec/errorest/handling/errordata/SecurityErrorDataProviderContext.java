package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.security.AccessDeniedErrorDataProvider;
import org.springframework.security.access.AccessDeniedException;

public class SecurityErrorDataProviderContext extends ErrorDataProviderContext {

    public SecurityErrorDataProviderContext(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof AccessDeniedException) {
            return new AccessDeniedErrorDataProvider(errorestProperties);
        }
        return super.getErrorDataProvider(ex);
    }
}
