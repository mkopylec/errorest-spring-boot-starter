package com.github.mkopylec.errorest.handlers;

import com.github.mkopylec.errorest.Error;
import com.github.mkopylec.errorest.ErrorData;
import com.github.mkopylec.errorest.Errors;
import com.github.mkopylec.errorest.ExceptionLogger;
import com.github.mkopylec.errorest.LoggingLevel;
import com.github.mkopylec.errorest.RestException;
import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.github.mkopylec.errorest.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.handlers.RequestMethodAttributeSettingFilter.REQUEST_METHOD_ERROR_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class ServletFilterErrorHandler extends BasicErrorController {

    public static final String REQUEST_URI_ERROR_ATTRIBUTE = "path";
    public static final String NOT_AVAILABLE_REQUEST_DATA = "[not available]";

    protected final ErrorAttributes errorAttributes;
    protected final ErrorestProperties errorestProperties;
    protected final ExceptionLogger exceptionLogger;

    public ServletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties, ErrorestProperties errorestProperties, ExceptionLogger exceptionLogger) {
        super(errorAttributes, serverProperties.getError());
        this.errorAttributes = errorAttributes;
        this.errorestProperties = errorestProperties;
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> response = super.error(request);
        ErrorData errorData = getErrorData(request);
        exceptionLogger.log(errorData);
        Errors errors = new Errors(errorData.getErrors());
        return status(errorData.getResponseStatus())
                .headers(response.getHeaders())
                .body(errors.toMap());
    }

    protected ErrorData getErrorData(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable throwable = errorAttributes.getError(requestAttributes);
        boolean restException = isRestException(throwable);
        LoggingLevel loggingLevel = restException
                ? toRestException(throwable).getLoggingLevel()
                : errorestProperties.getUnexpectedError().getLoggingLevel();
        String requestMethod = defaultIfBlank((String) requestAttributes.getAttribute(REQUEST_METHOD_ERROR_ATTRIBUTE, SCOPE_REQUEST), NOT_AVAILABLE_REQUEST_DATA);
        String requestUri = errorAttributes.getErrorAttributes(requestAttributes, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_REQUEST_DATA).toString();
        HttpStatus responseStatus = restException
                ? toRestException(throwable).getResponseHttpStatus()
                : getStatus(request);
        String errorCode = restException
                ? toRestException(throwable).getErrorCode()
                : errorestProperties.getUnexpectedError().getCode();
        String errorDescription = restException
                ? toRestException(throwable).getErrorDescription()
                : throwable.getMessage();
        boolean logStackTrace = restException
                ? toRestException(throwable).isLogStackTrace()
                : errorestProperties.getUnexpectedError().isLogStackTrace();

        return newErrorData()
                .withLoggingLevel(loggingLevel)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(responseStatus)
                .withThrowable(throwable)
                .withLogStackTrace(logStackTrace)
                .addError(new Error(errorCode, errorDescription))
                .build();
    }

    protected boolean isRestException(Throwable throwable) {
        return throwable instanceof RestException;
    }

    protected RestException toRestException(Throwable throwable) {
        return (RestException) throwable;
    }
}
