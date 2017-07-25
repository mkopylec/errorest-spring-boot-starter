package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import org.springframework.test.context.TestPropertySource
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

@TestPropertySource(properties = ['spring.mvc.throw-exception-if-no-handler-found: true', 'spring.mvc.static-path-pattern: /static/**'])
class NoHandlerFoundErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(NOT_FOUND)
                .hasErrorsId()
                .hasSingleError('HTTP_CLIENT_ERROR', description)

        where:
        uri                            | acceptHeader           | description
        '/controller/no-handler-found' | APPLICATION_JSON_VALUE | 'Not Found, no handler found for GET /controller/no-handler-found'
        '/controller/no-handler-found' | APPLICATION_XML_VALUE  | 'Not Found, no handler found for GET /controller/no-handler-found'
        '/filter/no-handler-found'     | APPLICATION_JSON_VALUE | 'Not Found, no handler found for GET /filter/no-handler-found'
        '/filter/no-handler-found'     | APPLICATION_XML_VALUE  | 'Not Found, no handler found for GET /filter/no-handler-found'
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
                .hasStatus(NOT_FOUND)
                .hasErrorsId()
                .hasSingleErrorWithoutDescription('HTTP_CLIENT_ERROR')

        where:
        uri                            | acceptHeader
        '/controller/no-handler-found' | APPLICATION_JSON_VALUE
        '/controller/no-handler-found' | APPLICATION_XML_VALUE
        '/filter/no-handler-found'     | APPLICATION_JSON_VALUE
        '/filter/no-handler-found'     | APPLICATION_XML_VALUE
    }
}