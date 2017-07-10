package com.github.mkopylec.errorest.handlers;

import com.github.mkopylec.errorest.Error;
import com.github.mkopylec.errorest.RestException;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class ServletFilterErrorHandler extends BasicErrorController {

    public static final String REQUEST_URI_ERROR_ATTRIBUTE = "path";
    public static final String NOT_AVAILABLE_REQUEST_DATA = "[not available]";

    protected final ErrorAttributes errorAttributes;

    public ServletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
        this.errorAttributes = errorAttributes;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> response = super.error(request);
        Map<String, Object> body = response.getBody();
        Error error;
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable ex = errorAttributes.getError(requestAttributes);
        HttpStatus httpStatus = getStatus(request);
        String requestUri = errorAttributes.getErrorAttributes(requestAttributes, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_REQUEST_DATA).toString();
        String requestMethod = defaultIfBlank((String) requestAttributes.getAttribute(RequestMethodAttributeSettingFilter.REQUEST_METHOD_ERROR_ATTRIBUTE, SCOPE_REQUEST), NOT_AVAILABLE_REQUEST_DATA);
        if (ex instanceof RestException) {
            RestException rex = (RestException) ex;
            error = new Error(rex.getErrorCode(), rex.getErrorDescription());
        }

        return status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(body);
    }
}
