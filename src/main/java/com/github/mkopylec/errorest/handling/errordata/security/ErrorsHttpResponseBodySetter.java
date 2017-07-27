package com.github.mkopylec.errorest.handling.errordata.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.response.Errors;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class ErrorsHttpResponseBodySetter {

    protected ObjectMapper jsonMapper = json().build();
    protected XmlMapper xmlMapper = xml().build();

    public void setErrorsResponseBody(ErrorData errorData, HttpServletRequest request, HttpServletResponse response) {
//        response.getWriter().
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    protected String toResponseBody(ErrorData errorData, HttpServletRequest request) {
        try {
            if (hasAcceptedType(APPLICATION_JSON, request)) {
                return jsonMapper.readValue(getResponseBodyAsString(), Errors.class);
            }
            if (hasAcceptedType(APPLICATION_XML, request)) {
                return xmlMapper.readValue(getResponseBodyAsString(), Errors.class);
            }
            throw new IOException("Incompatible HTTP response " + CONTENT_TYPE + " header: " + getResponseHeaders().getContentType());
        } catch (IOException e) {
            log.warn("Cannot convert HTTP response body to {}: {}", Errors.class.getName(), e.getMessage());
            return null;
        }
    }

    protected boolean hasAcceptedType(MediaType accept, HttpServletRequest request) {
        String acceptHeader = request.getHeader(ACCEPT);
        return isNotBlank(acceptHeader) && parseMediaType(acceptHeader).includes(accept);
    }
}
