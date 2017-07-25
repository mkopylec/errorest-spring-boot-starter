package com.github.mkopylec.errorest.assertions

import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException
import com.github.mkopylec.errorest.response.Error
import com.github.mkopylec.errorest.response.Errors
import org.springframework.http.HttpStatus

class ErrorHttpResponseAssert {

    private ExternalHttpRequestException actual

    protected ErrorHttpResponseAssert(ExternalHttpRequestException actual) {
        assert actual != null
        this.actual = actual
    }

    ErrorHttpResponseAssert hasStatus(HttpStatus status) {
        assert actual.statusCode == status
        return this
    }

    ErrorHttpResponseAssert hasErrorsId() {
        assert errors.id.length() == 10
        return this
    }

    ErrorHttpResponseAssert hasNoErrors() {
        assert errors.id == 'N/A'
        assert errors.errors.empty
        return this
    }

    ErrorHttpResponseAssert hasSingleErrorWithoutDescription(String code) {
        assert errors.errors.size() == 1
        assert errors.errors[0] == new Error(code, 'N/A')
        return this
    }

    ErrorHttpResponseAssert hasSingleError(String code, String description) {
        assert errors.errors.size() == 1
        assert errors.errors[0] == new Error(code, description)
        return this
    }

    ErrorHttpResponseAssert hasSingleErrorWithDescriptionPrefix(String code, String descriptionPrefix) {
        assert errors.errors.size() == 1
        assert errors.errors[0].hasCode(code)
        assert errors.errors[0].description.startsWith(descriptionPrefix)
        return this
    }

    private Errors getErrors() {
        return actual.responseBodyAsErrors
    }
}
