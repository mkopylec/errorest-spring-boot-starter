package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.exceptions.ErrorestResponseException;
import org.slf4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class ErrorestResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger log = getLogger(ErrorestResponseErrorHandler.class);

    protected final ObjectMapper jsonMapper;
    protected final XmlMapper xmlMapper;

    public ErrorestResponseErrorHandler() {
        jsonMapper = createJsonMapper();
        xmlMapper = createXmlMapper();
    }

    public ErrorestResponseErrorHandler(ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (HttpStatusCodeException ex) {
            ErrorestResponseException exception = replaceErrorestResponseException(ex, response);
            log.debug(ex.getMessage(), ex);
            throw exception;
        }
    }

    protected ErrorestResponseException replaceErrorestResponseException(HttpStatusCodeException ex, ClientHttpResponse response) {
        return new ErrorestResponseException(ex.getStatusCode(), ex.getStatusText(), ex.getResponseHeaders(), ex.getResponseBodyAsByteArray(), getCharset(response), jsonMapper, xmlMapper);
    }

    protected ObjectMapper createJsonMapper() {
        return json().build();
    }

    protected XmlMapper createXmlMapper() {
        return xml().build();
    }
}
