package com.github.mkopylec.errorest.handling;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.response.Errors;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class ServletFilterErrorHandler extends AbstractErrorController {

    protected final ErrorAttributes errorAttributes;
    protected final ErrorProperties errorProperties;
    protected final ErrorsFactory errorsFactory;
    protected final ErrorDataProviderContext providerContext;

    public ServletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties, ErrorsFactory errorsFactory, ErrorDataProviderContext providerContext) {
        super(errorAttributes, emptyList());
        this.errorAttributes = errorAttributes;
        errorProperties = serverProperties.getError();
        this.errorsFactory = errorsFactory;
        this.providerContext = providerContext;
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ResponseEntity<Errors> error(HttpServletRequest request) {
        ErrorData errorData = getErrorData(request);
        Errors errors = errorsFactory.logAndCreateErrors(errorData);
        return status(errorData.getResponseStatus())
                .body(errors);
    }

    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Throwable ex = errorAttributes.getError(webRequest);
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return provider.getErrorData(ex, request, getStatus(request), errorAttributes, webRequest);
    }
}
