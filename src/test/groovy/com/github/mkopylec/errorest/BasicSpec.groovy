package com.github.mkopylec.errorest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.github.mkopylec.errorest.application.RestApplication
import com.github.mkopylec.errorest.client.ErrorestTemplate
import com.github.mkopylec.errorest.configuration.ErrorestProperties
import com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat
import com.github.mkopylec.errorest.response.Error
import com.github.mkopylec.errorest.response.Errors
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpStatus.INSUFFICIENT_STORAGE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.xml

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = RestApplication)
abstract class BasicSpec extends Specification {

    private static ObjectMapper jsonMapper = json().build()
    private static XmlMapper xmlMapper = xml().build()

    @Rule
    public WireMockRule externalService = new WireMockRule(10000)

    @Autowired
    private ErrorestProperties properties
    @LocalServerPort
    private int port
    @Shared
    private RestTemplate restTemplate = new ErrorestTemplate()

    protected ResponseEntity<Errors> sendRequest(HttpMethod method, String uri, Map<String, String> headers = [:], Object body = null) {
        def url = "http://localhost:$port$uri"
        def httpHeaders = new HttpHeaders()
        headers.each { name, value -> httpHeaders.add(name, value) }
        def request = new HttpEntity<>(body, httpHeaders)
        return restTemplate.exchange(url, method, request, Errors)
    }

    protected void setResponseBodyFormat(ResponseBodyFormat bodyFormat) {
        properties.responseBodyFormat = bodyFormat
    }

    protected static void stubExternalService(String contentType) {
        def errors = [new Error('ERROR_CODE_1', 'Error 1'), new Error('ERROR_CODE_2', 'Error 2')]
        def body = new Errors('errors-id', errors)
        if (contentType == APPLICATION_JSON_VALUE) {
            body = jsonMapper.writeValueAsString(body)
        } else {
            body = xmlMapper.writeValueAsString(body)
        }
        stubFor(get(urlEqualTo('/external/resource'))
                .willReturn(
                aResponse()
                        .withStatus(INSUFFICIENT_STORAGE.value())
                        .withHeader(CONTENT_TYPE, contentType)
                        .withBody(body)
        ))
    }
}
