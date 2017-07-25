package com.github.mkopylec.errorest.specification

import com.github.mkopylec.errorest.BasicSpec
import com.github.mkopylec.errorest.exceptions.ErrorestResponseException
import spock.lang.Unroll

import static com.github.mkopylec.errorest.assertions.Assertions.assertThat
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.FULL
import static com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat.WITHOUT_DESCRIPTIONS
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE

class MissingServletRequestPartErrorsSpec extends BasicSpec {

    @Unroll
    def "Should get full error from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = FULL

        when:
        sendRequest POST, uri, [(ACCEPT): acceptHeader, (CONTENT_TYPE): "$MULTIPART_FORM_DATA_VALUE; boundary=gv"]

        then:
        def ex = thrown ErrorestResponseException
        assertThat(ex)
                .hasStatus(BAD_REQUEST)
                .hasErrorsId()
                .hasSingleError('HTTP_CLIENT_ERROR', 'Bad Request, required request part \'part\' is not present')

        where:
        uri                                        | acceptHeader
        '/controller/missing-servlet-request-part' | APPLICATION_JSON_VALUE
        '/controller/missing-servlet-request-part' | APPLICATION_XML_VALUE
        '/filter/missing-servlet-request-part'     | APPLICATION_JSON_VALUE
        '/filter/missing-servlet-request-part'     | APPLICATION_XML_VALUE
    }

    @Unroll
    def "Should get error without description from #uri request for 'Accept: #acceptHeader' header"() {
        given:
        responseBodyFormat = WITHOUT_DESCRIPTIONS

        when:
        sendRequest POST, uri, [(ACCEPT): acceptHeader, (CONTENT_TYPE): "$MULTIPART_FORM_DATA_VALUE; boundary=gv"]

        then:
        def ex = thrown ErrorestResponseException
        assertThat(ex)
                .hasStatus(BAD_REQUEST)
                .hasErrorsId()
                .hasSingleErrorWithoutDescription('HTTP_CLIENT_ERROR')

        where:
        uri                                        | acceptHeader
        '/controller/missing-servlet-request-part' | APPLICATION_JSON_VALUE
        '/controller/missing-servlet-request-part' | APPLICATION_XML_VALUE
        '/filter/missing-servlet-request-part'     | APPLICATION_JSON_VALUE
        '/filter/missing-servlet-request-part'     | APPLICATION_XML_VALUE
    }
}