package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import spock.lang.Unroll

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class NoErrorsSpec extends BasicSpec {

    @Unroll
    def "Should not get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        def response = sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        response.statusCode == OK
        !response.hasBody()

        where:
        uri                    | acceptHeader
        '/controller/no-error' | APPLICATION_JSON_VALUE
        '/controller/no-error' | APPLICATION_XML_VALUE
        '/filter/no-error'     | APPLICATION_JSON_VALUE
        '/filter/no-error'     | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should not get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        def response = sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        response.statusCode == OK
        !response.hasBody()

        where:
        uri                    | acceptHeader
        '/controller/no-error' | APPLICATION_JSON_VALUE
        '/controller/no-error' | APPLICATION_XML_VALUE
        '/filter/no-error'     | APPLICATION_JSON_VALUE
        '/filter/no-error'     | APPLICATION_XML_VALUE
    }
}