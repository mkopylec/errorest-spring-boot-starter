package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.errorest.exceptions.RestResponseException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

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
        return new RestResponseException(ex.getStatusCode(), ex.getStatusText(), ex.getResponseHeaders(), ex.getResponseBodyAsByteArray(), getCharset(response), mapper);
    }
}
