package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.RestResponseException
import com.github.mkopylec.errorest.response.Error
import spock.lang.Unroll

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE
import static org.springframework.http.MediaType.TEXT_XML_VALUE

class MediaTypeNotAcceptableErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == NOT_ACCEPTABLE
        ex.responseBodyAsErrors.errors == [new Error('HTTP_CLIENT_ERROR', description)]

        where:
        uri                                     | acceptHeader   | description
        '/controller/media-type-not-acceptable' | TEXT_XML_VALUE | 'HttpMediaTypeNotAcceptableException from controller'
        '/filter/media-type-not-acceptable'     | TEXT_XML_VALUE | 'HttpMediaTypeNotAcceptableException from servlet filer'
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == NOT_ACCEPTABLE
        ex.responseBodyAsErrors.errors == [new Error('HTTP_CLIENT_ERROR', 'N/A')]

        where:
        uri                                     | acceptHeader
        '/controller/media-type-not-acceptable' | APPLICATION_JSON_VALUE
        '/controller/media-type-not-acceptable' | APPLICATION_XML_VALUE
        '/filter/media-type-not-acceptable'     | APPLICATION_JSON_VALUE
        '/filter/media-type-not-acceptable'     | APPLICATION_XML_VALUE
    }
}