package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.exceptions.RestException;

public class ErrorDataProviderContext {

    protected final ErrorestProperties errorestProperties;

    public ErrorDataProviderContext(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public <T extends Throwable> ErrorDataProvider getErrorDataProvider(T ex) {
        if (ex instanceof RestException) {
            return new RestExceptionErrorDataProvider(errorestProperties);
        }
        return new ThrowableErrorDataProvider(errorestProperties);
    }
}
