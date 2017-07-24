package com.github.mkopylec.errorest.client;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ErrorestTemplate extends RestTemplate {

    public ErrorestTemplate() {
        replaceErrorHandler();
    }

    public ErrorestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        replaceErrorHandler();
    }

    public ErrorestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        replaceErrorHandler();
    }

    protected void replaceErrorHandler() {
        setErrorHandler(new ErrorestResponseErrorHandler());
    }
}
