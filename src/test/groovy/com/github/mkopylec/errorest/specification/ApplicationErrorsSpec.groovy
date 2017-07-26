package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class ApplicationErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(I_AM_A_TEAPOT)
                .hasErrorsId()
                .hasMultipleErrors([ERROR_CODE_1: 'Error 1', ERROR_CODE_2: 'Error 2'])

        where:
        uri                       | acceptHeader
        '/controller/application' | APPLICATION_JSON_VALUE
        '/controller/application' | APPLICATION_XML_VALUE
        '/filter/application'     | APPLICATION_JSON_VALUE
        '/filter/application'     | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(I_AM_A_TEAPOT)
                .hasErrorsId()
                .hasMultipleErrorsWithoutDescriptions(['ERROR_CODE_1', 'ERROR_CODE_2'])

        where:
        uri                       | acceptHeader
        '/controller/application' | APPLICATION_JSON_VALUE
        '/controller/application' | APPLICATION_XML_VALUE
        '/filter/application'     | APPLICATION_JSON_VALUE
        '/filter/application'     | APPLICATION_XML_VALUE
    }
}