package com.github.mkopylec.errorest.handling.errordata.security;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.logging.ExceptionLogger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorestAccessDeniedHandler implements AccessDeniedHandler {

    protected final ErrorDataProviderContext providerContext;
    protected final ExceptionLogger logger;

    public ErrorestAccessDeniedHandler(ErrorDataProviderContext providerContext, ExceptionLogger logger) {
        this.providerContext = providerContext;
        this.logger = logger;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {

    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(AccessDeniedException ex, HttpServletRequest request) {
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return provider.getErrorData(ex, request);
    }
}
