package com.github.mkopylec.errorest.exceptions;

import com.github.mkopylec.errorest.response.Error;
import com.github.mkopylec.errorest.response.Errors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;
import java.util.List;

import static com.github.mkopylec.errorest.response.Errors.emptyErrors;

public class RestResponseException extends HttpStatusCodeException {

    protected final Errors errors;

    public RestResponseException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset, Errors errors) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
        this.errors = errors == null ? emptyErrors() : errors;
    }

    public List<Error> getErrors() {
        return errors.getErrors();
    }

    public boolean hasErrors() {
        return errors.hasErrors();
    }

    public boolean containsErrorCode(String code) {
        return errors.containsErrorCode(code);
    }

    public boolean containsErrorDescription(String description) {
        return errors.containsErrorDescription(description);
    }
}
