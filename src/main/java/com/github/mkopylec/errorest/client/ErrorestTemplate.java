package com.github.mkopylec.errorest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml;

public class ErrorestTemplate extends RestTemplate {

    protected ObjectMapper jsonMapper = json().build();
    protected XmlMapper xmlMapper = xml().build();

    public ErrorestTemplate() {
        super();
    }

    public ErrorestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public ErrorestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        } catch (HttpStatusCodeException ex) {
            throw createExternalHttpRequestException(method, url, ex);
        }
    }

    protected ExternalHttpRequestException createExternalHttpRequestException(HttpMethod method, URI url, HttpStatusCodeException ex) {
        return new ExternalHttpRequestException(method, url.toString(), ex.getStatusCode(), ex.getStatusText(), ex.getResponseHeaders(), ex.getResponseBodyAsByteArray(), getCharset(ex), jsonMapper, xmlMapper);
    }

    protected Charset getCharset(HttpStatusCodeException ex) {
        MediaType contentType = ex.getResponseHeaders().getContentType();
        return contentType != null ? contentType.getCharset() : null;
    }
}
