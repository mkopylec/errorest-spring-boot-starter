package com.github.mkopylec.errorest.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.response.Errors;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.mkopylec.errorest.response.Errors.emptyErrors;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

public class ExternalHttpRequestException extends HttpStatusCodeException {

    private static final Logger log = getLogger(ExternalHttpRequestException.class);

    protected final HttpMethod method;
    protected final String url;
    protected final ObjectMapper jsonMapper;
    protected final XmlMapper xmlMapper;
    protected Errors errors;

    public ExternalHttpRequestException(HttpMethod method, String url, HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset, ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
        this.method = method;
        this.url = url;
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
    }

    public Errors getResponseBodyAsErrors() {
        if (errors != null) {
            return errors;
        }
        errors = parseResponseBody();
        if (errors == null) {
            return emptyErrors();
        }
        return errors;
    }

    @Override
    public String getMessage() {
        return "An external HTTP request has failed: " + method + " " + url + " " + getStatusCode() + ". Response body: " + getResponseBodyAsString();
    }

    protected Errors parseResponseBody() {
        try {
            if (hasContentType(APPLICATION_JSON)) {
                return jsonMapper.readValue(getResponseBodyAsString(), Errors.class);
            }
            if (hasContentType(APPLICATION_XML)) {
                return xmlMapper.readValue(getResponseBodyAsString(), Errors.class);
            }
            throw new IOException("Incompatible HTTP response " + CONTENT_TYPE + " header: " + getResponseHeaders().getContentType());
        } catch (IOException e) {
            log.warn("Cannot convert HTTP response body to {}: {}", Errors.class.getName(), e.getMessage());
            return null;
        }
    }

    protected boolean hasContentType(MediaType mediaType) {
        MediaType contentType = getResponseHeaders().getContentType();
        return contentType == null || contentType.includes(mediaType);
    }
}
