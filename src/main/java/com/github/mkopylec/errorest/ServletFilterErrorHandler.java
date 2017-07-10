package com.github.mkopylec.errorest;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@Controller
@EnableConfigurationProperties(ServerProperties.class)
public class ServletFilterErrorHandler extends BasicErrorController {

    public static final String REQUEST_PATH_ERROR_ATTRIBUTE = "path";
    public static final String EXCEPTION_ERROR_ATTRIBUTE = "exception";

    public ServletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties server) {
        super(errorAttributes, server.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> response = super.error(request);
        Map<String, Object> body = response.getBody();
        Error error;
        Object ex = body.get(EXCEPTION_ERROR_ATTRIBUTE);
        if (ex instanceof RestException) {
            RestException rex = (RestException) ex;
            error = new Error(rex.getErrorCode(), rex.getErrorDescription());
        }

        return status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(body);
    }
}
