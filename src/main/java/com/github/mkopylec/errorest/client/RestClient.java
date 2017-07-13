package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

public class RestClient extends RestTemplate {

    protected final ObjectMapper mapper;

    public RestClient() {
        this(createObjectMapper());
    }

    public RestClient(ClientHttpRequestFactory requestFactory) {
        this(requestFactory, createObjectMapper());
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters) {
        this(messageConverters, createObjectMapper());
    }

    public RestClient(ObjectMapper mapper) {
        this.mapper = mapper;
        replaceErrorHandler();
    }

    public RestClient(ClientHttpRequestFactory requestFactory, ObjectMapper mapper) {
        super(requestFactory);
        this.mapper = mapper;
        replaceErrorHandler();
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters, ObjectMapper mapper) {
        super(messageConverters);
        this.mapper = mapper;
        replaceErrorHandler();
    }

    protected void replaceErrorHandler() {
        setErrorHandler(new RestResponseErrorHandler(mapper));
    }

    protected static ObjectMapper createObjectMapper() {
        return json().build();
    }
}
