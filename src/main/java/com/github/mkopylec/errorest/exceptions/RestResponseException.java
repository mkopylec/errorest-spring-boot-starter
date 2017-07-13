package com.github.mkopylec.errorest.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.errorest.response.Errors;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.mkopylec.errorest.response.Errors.emptyErrors;
import static org.slf4j.LoggerFactory.getLogger;

public class RestResponseException extends HttpStatusCodeException {

    private static final Logger log = getLogger(RestResponseException.class);

    protected final ObjectMapper mapper;
    protected Errors errors;

    public RestResponseException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset, ObjectMapper mapper) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
        this.mapper = mapper;
    }

    public Errors getResponseBodyAsErrors() {
        if (errors != null) {
            return errors;
        }
        errors = parseResponseBody();
        if (errors == null) {
            errors = emptyErrors();
        }
        return errors;
    }

    protected Errors parseResponseBody() {
        try {
            return mapper.readValue(getResponseBodyAsString(), Errors.class);
        } catch (IOException e) {
            log.warn("Cannot convert HTTP response body to " + Errors.class.getName(), e);
            return null;
        }
    }
}
