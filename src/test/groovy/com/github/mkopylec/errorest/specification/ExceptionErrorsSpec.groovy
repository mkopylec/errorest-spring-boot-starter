package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.RestResponseException
import com.github.mkopylec.errorest.response.Error
import spock.lang.Unroll

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class ExceptionErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == INTERNAL_SERVER_ERROR
        ex.responseBodyAsErrors.errors == [new Error('UNEXPECTED_ERROR', description)]

        where:
        uri                     | acceptHeader           | description
//        '/controller/exception' | APPLICATION_JSON_VALUE | 'Exception from controller'
//        '/controller/exception' | APPLICATION_XML_VALUE  | 'Exception from controller'
//        '/filter/exception'     | APPLICATION_JSON_VALUE | 'Exception from servlet filer'
        '/filter/exception'     | APPLICATION_XML_VALUE  | 'Exception from servlet filer'
    }
}