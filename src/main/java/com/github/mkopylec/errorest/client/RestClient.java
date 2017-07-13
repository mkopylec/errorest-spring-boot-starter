package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class RestClient extends RestTemplate {

    protected final ObjectMapper jsonMapper;
    protected final XmlMapper xmlMapper;

    public RestClient() {
        this(createJsonMapper(), createXmlMapper());
    }

    public RestClient(ClientHttpRequestFactory requestFactory) {
        this(requestFactory, createJsonMapper(), createXmlMapper());
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters) {
        this(messageConverters, createJsonMapper(), createXmlMapper());
    }

    public RestClient(ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
        replaceErrorHandler();
    }

    public RestClient(ClientHttpRequestFactory requestFactory, ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        super(requestFactory);
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
        replaceErrorHandler();
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters, ObjectMapper jsonMapper, XmlMapper xmlMapper) {
        super(messageConverters);
        this.jsonMapper = jsonMapper;
        this.xmlMapper = xmlMapper;
        replaceErrorHandler();
    }

    protected void replaceErrorHandler() {
        setErrorHandler(new RestResponseErrorHandler(jsonMapper, xmlMapper));
    }

    protected static ObjectMapper createJsonMapper() {
        return json().build();
    }

    protected static XmlMapper createXmlMapper() {
        return xml().build();
    }
}
