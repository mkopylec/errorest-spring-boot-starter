package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class MethodArgumentNotValidErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest POST, uri, [(ACCEPT): acceptHeader], [message: null, number: 11]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(UNPROCESSABLE_ENTITY)
                .hasErrorsId()
                .hasMultipleErrors([EMPTY_MESSAGE: 'Invalid \'message\' value: null', NUMBER_TOO_BIG: 'Invalid \'number\' value: 11'])

        where:
        uri                                     | acceptHeader
        '/controller/method-argument-not-valid' | APPLICATION_JSON_VALUE
        '/controller/method-argument-not-valid' | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest POST, uri, [(ACCEPT): acceptHeader], [message: null, number: 11]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(UNPROCESSABLE_ENTITY)
                .hasErrorsId()
                .hasMultipleErrorsWithoutDescriptions(['EMPTY_MESSAGE', 'NUMBER_TOO_BIG'])

        where:
        uri                                     | acceptHeader
        '/controller/method-argument-not-valid' | APPLICATION_JSON_VALUE
        '/controller/method-argument-not-valid' | APPLICATION_XML_VALUE
    }
}