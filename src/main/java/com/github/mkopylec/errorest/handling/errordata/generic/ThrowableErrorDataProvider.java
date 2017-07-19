package com.github.mkopylec.errorest.handling.errordata.generic;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static com.github.mkopylec.errorest.handling.errordata.http.HttpClientErrorDataProvider.HTTP_CLIENT_ERROR_CODE;
import static com.github.mkopylec.errorest.logging.LoggingLevel.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ThrowableErrorDataProvider extends ErrorDataProvider<Throwable> {

    public static final String UNEXPECTED_ERROR_CODE = "UNEXPECTED_ERROR";

    public ThrowableErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    @Override
    public ErrorData getErrorData(Throwable ex, HttpServletRequest request) {
        return buildErrorData(ex, INTERNAL_SERVER_ERROR)
                .withRequestMethod(request.getMethod())
                .withRequestUri(request.getRequestURI())
                .build();
    }

    @Override
    public ErrorData getErrorData(Throwable ex, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, RequestAttributes requestAttributes) {
        String requestMethod = getRequestMethod(requestAttributes);
        String requestUri = getRequestUri(errorAttributes, requestAttributes);
        return buildErrorData(ex, defaultResponseStatus)
                .withRequestMethod(requestMethod)
                .withRequestUri(requestUri)
                .withResponseStatus(defaultResponseStatus)
                .build();
    }

    protected ErrorDataBuilder buildErrorData(Throwable ex, HttpStatus responseHttpStatus) {
        return newErrorData()
                .withLoggingLevel(getLoggingLevel(responseHttpStatus))
                .withResponseStatus(responseHttpStatus)
                .withThrowable(ex)
                .withLogStackTrace(isLogStackTrace(responseHttpStatus))
                .addError(new Error(getErrorCode(responseHttpStatus), getErrorDescription(ex, responseHttpStatus)));
    }

    protected LoggingLevel getLoggingLevel(HttpStatus responseHttpStatus) {
        return responseHttpStatus.is4xxClientError() ? errorestProperties.getHttpClientError().getLoggingLevel() : ERROR;
    }

    protected boolean isLogStackTrace(HttpStatus responseHttpStatus) {
        return !responseHttpStatus.is4xxClientError() || errorestProperties.getHttpClientError().isLogStackTrace();
    }

    protected String getErrorCode(HttpStatus responseHttpStatus) {
        return responseHttpStatus.is4xxClientError() ? HTTP_CLIENT_ERROR_CODE : UNEXPECTED_ERROR_CODE;
    }

    protected String getErrorDescription(Throwable ex, HttpStatus responseHttpStatus) {
        return ex == null ? responseHttpStatus.getReasonPhrase() : ex.getMessage();
    }
}
