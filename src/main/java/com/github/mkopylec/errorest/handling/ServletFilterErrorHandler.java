package com.github.mkopylec.errorest.handling;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.providers.ErrorData;
import com.github.mkopylec.errorest.handling.providers.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.providers.ErrorDataProviderContext;
import com.github.mkopylec.errorest.logging.ExceptionLogger;
import com.github.mkopylec.errorest.response.Errors;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

public class ServletFilterErrorHandler extends BasicErrorController {

    protected final ErrorAttributes errorAttributes;
    protected final ErrorestProperties errorestProperties;
    protected final ExceptionLogger logger;
    protected final ErrorDataProviderContext providerContext;

    public ServletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties, ErrorestProperties errorestProperties, ExceptionLogger logger, ErrorDataProviderContext providerContext) {
        super(errorAttributes, serverProperties.getError());
        this.errorAttributes = errorAttributes;
        this.errorestProperties = errorestProperties;
        this.logger = logger;
        this.providerContext = providerContext;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ErrorData errorData = getErrorData(request);
        logger.log(errorData);
        ResponseEntity<Map<String, Object>> response = super.error(request);
        Errors errors = new Errors(errorData.getErrors());
        return status(errorData.getResponseStatus())
                .headers(response.getHeaders())
                .body(errors.toMap());
    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable ex = errorAttributes.getError(requestAttributes);
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return provider.getErrorData(ex, getStatus(request), errorAttributes, requestAttributes);
    }
}
