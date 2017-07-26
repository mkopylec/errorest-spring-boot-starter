package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.RequestAttributeSettingFilter.AUTHORIZATION_HEADER_ERROR_ATTRIBUTE;
import static com.github.mkopylec.errorest.handling.RequestAttributeSettingFilter.REQUEST_METHOD_ERROR_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public abstract class ErrorDataProvider<T extends Throwable> {

    public static final String REQUEST_URI_ERROR_ATTRIBUTE = "path";
    public static final String NOT_AVAILABLE_DATA = "[N/A]";

    protected final ErrorestProperties errorestProperties;

    protected ErrorDataProvider(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public abstract ErrorData getErrorData(T ex, HttpServletRequest request);

    public abstract ErrorData getErrorData(T ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes);

    protected String getRequestUri(ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        return errorAttributes.getErrorAttributes(requestAttributes, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_DATA).toString();
    }

    protected String getRequestMethod(RequestAttributes requestAttributes) {
        return getAttribute(REQUEST_METHOD_ERROR_ATTRIBUTE, requestAttributes);
    }

    protected String getAuthorizationHeader(RequestAttributes requestAttributes) {
        return getAttribute(AUTHORIZATION_HEADER_ERROR_ATTRIBUTE, requestAttributes);
    }

    protected String getAttribute(String name, RequestAttributes requestAttributes) {
        return defaultIfBlank((String) requestAttributes.getAttribute(name, SCOPE_REQUEST), NOT_AVAILABLE_DATA);
    }
}
