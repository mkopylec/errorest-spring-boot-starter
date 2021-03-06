package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE

class AccessDeniedErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader, (AUTHORIZATION): createAuthorization()]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(FORBIDDEN)
                .hasErrorsId()
                .hasSingleErrorWithDescriptionPrefix('SECURITY_ERROR', 'Access denied for request with headers: {')

        where:
        uri                                           | acceptHeader
        '/controller/access-denied-via-configuration' | APPLICATION_JSON_VALUE
        '/controller/access-denied-via-configuration' | APPLICATION_XML_VALUE
        '/controller/access-denied-via-annotation'    | APPLICATION_JSON_VALUE
        '/controller/access-denied-via-annotation'    | APPLICATION_XML_VALUE
        '/filter/access-denied'                       | APPLICATION_JSON_VALUE
        '/filter/access-denied'                       | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest GET, uri, [(ACCEPT): acceptHeader, (AUTHORIZATION): createAuthorization()]

        then:
        def ex = thrown ExternalHttpRequestException
        assertThat(ex)
                .hasStatus(FORBIDDEN)
                .hasErrorsId()
                .hasSingleErrorWithoutDescription('SECURITY_ERROR')

        where:
        uri                                           | acceptHeader
        '/controller/access-denied-via-configuration' | APPLICATION_JSON_VALUE
        '/controller/access-denied-via-configuration' | APPLICATION_XML_VALUE
        '/controller/access-denied-via-annotation'    | APPLICATION_JSON_VALUE
        '/controller/access-denied-via-annotation'    | APPLICATION_XML_VALUE
        '/filter/access-denied'                       | APPLICATION_JSON_VALUE
        '/filter/access-denied'                       | APPLICATION_XML_VALUE
    }

    private static String createAuthorization() {
        def authorization = 'Basic ' + 'user:password'.bytes.encodeBase64().toString()
        authorization
    }
}