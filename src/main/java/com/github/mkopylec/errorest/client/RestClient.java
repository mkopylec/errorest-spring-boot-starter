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

    protected ObjectMapper jsonMapper;
    protected XmlMapper xmlMapper;

    public RestClient() {
        initialize();
    }

    public RestClient(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        initialize();
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        initialize();
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    protected void initialize() {
        jsonMapper = createJsonMapper();
        xmlMapper = createXmlMapper();
        replaceErrorHandler();
    }

    protected ObjectMapper createJsonMapper() {
        return json().build();
    }

    protected XmlMapper createXmlMapper() {
        return xml().build();
    }

    protected void replaceErrorHandler() {
        setErrorHandler(new RestResponseErrorHandler(jsonMapper, xmlMapper));
    }
}
