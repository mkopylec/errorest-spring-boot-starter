package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class TypeMismatchErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(BAD_REQUEST)
                .hasErrorsId()
                .hasSingleError('HTTP_CLIENT_ERROR', 'Bad Request, failed to convert value of type \'java.lang.String\' to required type \'int\'; nested exception is java.lang.NumberFormatException: For input string: "abc"')

        where:
        uri                                             | acceptHeader
        '/controller/type-mismatch?query-parameter=abc' | APPLICATION_JSON_VALUE
        '/controller/type-mismatch?query-parameter=abc' | APPLICATION_XML_VALUE
        '/filter/type-mismatch?query-parameter=abc'     | APPLICATION_JSON_VALUE
        '/filter/type-mismatch?query-parameter=abc'     | APPLICATION_XML_VALUE
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
                .hasStatus(BAD_REQUEST)
                .hasErrorsId()
                .hasSingleErrorWithoutDescription('HTTP_CLIENT_ERROR')

        where:
        uri                                             | acceptHeader
        '/controller/type-mismatch?query-parameter=abc' | APPLICATION_JSON_VALUE
        '/controller/type-mismatch?query-parameter=abc' | APPLICATION_XML_VALUE
        '/filter/type-mismatch?query-parameter=abc'     | APPLICATION_JSON_VALUE
        '/filter/type-mismatch?query-parameter=abc'     | APPLICATION_XML_VALUE
    }
}