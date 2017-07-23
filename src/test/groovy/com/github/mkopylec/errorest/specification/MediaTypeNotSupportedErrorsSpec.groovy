package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.RestResponseException
import com.github.mkopylec.errorest.response.Error
import spock.lang.Unroll

import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE
import static org.springframework.http.MediaType.TEXT_HTML_VALUE

class MediaTypeNotSupportedErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader, (CONTENT_TYPE): TEXT_HTML_VALUE]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == UNSUPPORTED_MEDIA_TYPE
        ex.responseBodyAsErrors.errors == [new Error('HTTP_CLIENT_ERROR', 'Unsupported Media Type: text/html, supported media types are text/plain')]

        where:
        uri                                    | acceptHeader
        '/controller/media-type-not-supported' | APPLICATION_JSON_VALUE
        '/controller/media-type-not-supported' | APPLICATION_XML_VALUE
        '/filter/media-type-not-supported'     | APPLICATION_JSON_VALUE
        '/filter/media-type-not-supported'     | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader, (CONTENT_TYPE): TEXT_HTML_VALUE]

        then:
        def ex = thrown RestResponseException
        ex.statusCode == UNSUPPORTED_MEDIA_TYPE
        ex.responseBodyAsErrors.errors == [new Error('HTTP_CLIENT_ERROR', 'N/A')]

        where:
        uri                                    | acceptHeader
        '/controller/media-type-not-supported' | APPLICATION_JSON_VALUE
        '/controller/media-type-not-supported' | APPLICATION_XML_VALUE
        '/filter/media-type-not-supported'     | APPLICATION_JSON_VALUE
        '/filter/media-type-not-supported'     | APPLICATION_XML_VALUE
    }
}