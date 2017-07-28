package com.github.mkopylec.errorest.handling.errordata.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.response.Errors;
import org.slf4j.Logger;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class ErrorsHttpResponseBodySetter {

    private static final Logger log = getLogger(ErrorsHttpResponseBodySetter.class);

    protected ObjectMapper jsonMapper = json().build();
    protected XmlMapper xmlMapper = xml().build();

    public void setErrorsResponseBody(Errors errors, HttpServletRequest request, HttpServletResponse response) {
//        response.getWriter().
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    protected String toResponseBody(Errors errors, HttpServletRequest request) {
        String acceptHeader = request.getHeader(ACCEPT);
        try {
            if (hasAcceptedType(APPLICATION_JSON, acceptHeader)) {
                return jsonMapper.writeValueAsString(errors);
            }
            if (hasAcceptedType(APPLICATION_XML, acceptHeader)) {
                return xmlMapper.writeValueAsString(errors);
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
}
