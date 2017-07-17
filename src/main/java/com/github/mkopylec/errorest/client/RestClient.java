package com.github.mkopylec.errorest.client;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestClient extends RestTemplate {

    public RestClient() {
        replaceErrorHandler();
    }

    public RestClient(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        replaceErrorHandler();
    }

    public RestClient(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        replaceErrorHandler();
    }

    protected void replaceErrorHandler() {
        setErrorHandler(new RestResponseErrorHandler());
    }
}
