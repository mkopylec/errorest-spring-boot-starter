package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.MediaType.TEXT_XML_VALUE

class MediaTypeNotAcceptableErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get empty error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(NOT_ACCEPTABLE)
                .hasNoErrors()

        where:
        uri                                     | acceptHeader
        '/controller/media-type-not-acceptable' | TEXT_XML_VALUE
        '/filter/media-type-not-acceptable'     | TEXT_XML_VALUE
    }
}