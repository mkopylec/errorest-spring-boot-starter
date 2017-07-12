package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.handling.ErrorData;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

public interface ErrorDataProvider<T extends Throwable> {

    String REQUEST_URI_ERROR_ATTRIBUTE = "path";
    String NOT_AVAILABLE_REQUEST_DATA = "[not available]";

    ErrorData getErrorData(T ex, HttpServletRequest request);

    ErrorData getErrorData(T ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes);
}
