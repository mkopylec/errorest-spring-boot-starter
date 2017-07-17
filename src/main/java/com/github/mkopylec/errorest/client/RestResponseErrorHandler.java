package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.exceptions.RestResponseException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

    protected final ObjectMapper jsonMapper;
    protected final XmlMapper xmlMapper;

    public RestResponseErrorHandler() {
        jsonMapper = createJsonMapper();
        xmlMapper = createXmlMapper();
    }

    public RestResponseErrorHandler(ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (HttpStatusCodeException ex) {
            // TODO log exception with body
            throw replaceWithRestResponseException(ex, response);
        }
    }

    protected RestResponseException replaceWithRestResponseException(HttpStatusCodeException ex, ClientHttpResponse response) {
        return new RestResponseException(ex.getStatusCode(), ex.getStatusText(), ex.getResponseHeaders(), ex.getResponseBodyAsByteArray(), getCharset(response), jsonMapper, xmlMapper);
    }

    protected ObjectMapper createJsonMapper() {
        return json().build();
    }

    protected XmlMapper createXmlMapper() {
        return xml().build();
    }
}
