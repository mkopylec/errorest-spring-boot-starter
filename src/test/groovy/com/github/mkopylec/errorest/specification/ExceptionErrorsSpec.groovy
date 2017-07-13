package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.RestResponseException
import com.github.mkopylec.errorest.response.Error
import spock.lang.Unroll

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.SIMPLE
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class ExceptionErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get error from controller for #bodyFormat response body format and 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = bodyFormat

        when:
        sendRequest GET, '/controller/exception', [(ACCEPT): acceptHeader]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == INTERNAL_SERVER_ERROR
        ex.responseBodyAsErrors.errors == [new Error('UNEXPECTED_ERROR', 'Exception from controller')]

        where:
        bodyFormat | acceptHeader
        SIMPLE     | APPLICATION_JSON_VALUE
        SIMPLE     | APPLICATION_XML_VALUE
        FULL       | APPLICATION_JSON_VALUE
        FULL       | APPLICATION_XML_VALUE
    }
}