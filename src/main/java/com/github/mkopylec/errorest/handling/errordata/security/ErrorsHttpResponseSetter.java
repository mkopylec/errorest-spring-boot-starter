package com.github.mkopylec.errorest.handling.errordata.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.response.Errors;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class ErrorsHttpResponseSetter {

    private static final Logger log = getLogger(ErrorsHttpResponseSetter.class);

    protected ObjectMapper jsonMapper = json().build();
    protected XmlMapper xmlMapper = xml().build();

    public void setErrorsResponse(Errors errors, HttpStatus responseHttpStatus, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(responseHttpStatus.value());
        HttpResponseData responseData = getResponseData(errors, request);
        if (responseData != null) {
            response.addHeader(CONTENT_TYPE, responseData.getContentType());
            response.getWriter().write(responseData.getBody());
            response.getWriter().flush();
        }
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    protected HttpResponseData getResponseData(Errors errors, HttpServletRequest request) {
        String acceptHeader = request.getHeader(ACCEPT);
        try {
            if (hasAcceptedType(APPLICATION_JSON, acceptHeader)) {
                String body = jsonMapper.writeValueAsString(errors);
                return new HttpResponseData(APPLICATION_JSON_VALUE, body);
            }
            if (hasAcceptedType(APPLICATION_XML, acceptHeader)) {
                String body = xmlMapper.writeValueAsString(errors);
                return new HttpResponseData(APPLICATION_XML_VALUE, body);
            }
            throw new IOException("Incompatible HTTP request " + ACCEPT + " header: " + acceptHeader);
        } catch (IOException e) {
            log.warn("Cannot convert {} to HTTP response body: {}", errors, e.getMessage());
            return null;
        }
    }

    protected boolean hasAcceptedType(MediaType accept, String acceptHeader) {
        return isNotBlank(acceptHeader) && parseMediaType(acceptHeader).includes(accept);
    }

    public static class HttpResponseData {

        protected final String contentType;
        protected final String body;

        protected HttpResponseData(String contentType, String body) {
            this.contentType = contentType;
            this.body = body;
        }

        protected String getContentType() {
            return contentType;
        }

        protected String getBody() {
            return body;
        }
    }
}
