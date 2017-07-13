package com.github.mkopylec.errorest

import com.github.mkopylec.errorest.application.RestApplication
import com.github.mkopylec.errorest.client.RestClient
import com.github.mkopylec.errorest.configuration.ErrorestProperties
import com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat
import com.github.mkopylec.errorest.response.Errors
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = RestApplication)
abstract class BasicSpec extends Specification {

    @Autowired
    private ErrorestProperties properties
    @LocalServerPort
    private int port
    @Shared
    private RestTemplate restTemplate = new RestClient()

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
}
