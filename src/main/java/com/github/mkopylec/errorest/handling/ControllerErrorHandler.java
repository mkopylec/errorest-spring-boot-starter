package com.github.mkopylec.errorest.handling;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.response.Errors;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ControllerErrorHandler {

    protected final ErrorsFactory errorsFactory;
    protected final ErrorDataProviderContext providerContext;

    public ControllerErrorHandler(ErrorsFactory errorsFactory, ErrorDataProviderContext providerContext) {
        this.errorsFactory = errorsFactory;
        this.providerContext = providerContext;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Errors> handleThrowable(Throwable ex, HttpServletRequest request) {
        ErrorData errorData = getErrorData(ex, request);
        Errors errors = errorsFactory.logAndCreateErrors(errorData);
        return status(errorData.getResponseStatus())
                .body(errors);
    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(Throwable ex, HttpServletRequest request) {
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return provider.getErrorData(ex, request);
    }
}
