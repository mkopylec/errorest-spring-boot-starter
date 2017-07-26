package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.INSUFFICIENT_STORAGE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class ExternalRequestErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL
        stubExternalService(acceptHeader)

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(INSUFFICIENT_STORAGE)
                .hasErrorsId()
                .hasMultipleErrors([ERROR_CODE_1: 'Error 1', ERROR_CODE_2: 'Error 2'])

        where:
        uri                            | acceptHeader
        '/controller/external-request' | APPLICATION_JSON_VALUE
        '/controller/external-request' | APPLICATION_XML_VALUE
        '/filter/external-request'     | APPLICATION_JSON_VALUE
        '/filter/external-request'     | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS
        stubExternalService(acceptHeader)

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(INSUFFICIENT_STORAGE)
                .hasErrorsId()
                .hasMultipleErrorsWithoutDescriptions(['ERROR_CODE_1', 'ERROR_CODE_2'])

        where:
        uri                            | acceptHeader
        '/controller/external-request' | APPLICATION_JSON_VALUE
        '/controller/external-request' | APPLICATION_XML_VALUE
        '/filter/external-request'     | APPLICATION_JSON_VALUE
        '/filter/external-request'     | APPLICATION_XML_VALUE
    }
}