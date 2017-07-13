package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.errorest.exceptions.RestResponseException;
import com.github.mkopylec.errorest.response.Errors;
import org.slf4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger log = getLogger(RestResponseErrorHandler.class);

    protected final ObjectMapper mapper;

    public RestResponseErrorHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (HttpStatusCodeException ex) {
            throw replaceWithRestResponseException(ex, response);
        }
    }

    protected RestResponseException replaceWithRestResponseException(HttpStatusCodeException ex, ClientHttpResponse response) {
        Errors errors = null;
        try {
            errors = mapper.readValue(ex.getResponseBodyAsByteArray(), Errors.class);
        } catch (IOException e) {
            log.warn("Cannot convert HTTP response body to " + Errors.class.getName(), e);
        }
        return new RestResponseException(ex.getStatusCode(), ex.getStatusText(), ex.getResponseHeaders(), ex.getResponseBodyAsByteArray(), getCharset(response), errors);
    }
}
