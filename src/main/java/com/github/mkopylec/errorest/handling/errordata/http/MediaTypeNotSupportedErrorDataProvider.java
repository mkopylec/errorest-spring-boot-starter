package com.github.mkopylec.errorest.handling.errordata.http;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.join;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

public class MediaTypeNotSupportedErrorDataProvider extends HttpClientErrorDataProvider<HttpMediaTypeNotSupportedException> {

    public MediaTypeNotSupportedErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return getErrorData(ex, request, UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    public ErrorData getErrorData(HttpMediaTypeNotSupportedException ex, HttpServletRequest request, HttpStatus responseHttpStatus, ErrorAttributes errorAttributes, WebRequest webRequest) {
        return super.getErrorData(ex, request, UNSUPPORTED_MEDIA_TYPE, errorAttributes, webRequest);
    }

    @Override
    protected String getErrorDescription(HttpMediaTypeNotSupportedException ex) {
        return UNSUPPORTED_MEDIA_TYPE.getReasonPhrase() + ": " + ex.getContentType() + ", supported media types are " + join(ex.getSupportedMediaTypes(), ", ");
    }
}
