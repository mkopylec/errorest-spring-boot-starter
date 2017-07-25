package com.github.mkopylec.errorest.assertions

import com.github.mkopylec.errorest.exceptions.ErrorestResponseException
import com.github.mkopylec.errorest.response.Error
import com.github.mkopylec.errorest.response.Errors
import org.springframework.http.HttpStatus

class ErrorestResponseAssert {

    private final ErrorestResponseException actual

    protected ErrorestResponseAssert(ErrorestResponseException actual) {
        assert actual != null
        this.actual = actual
    }

    ErrorestResponseAssert hasStatus(HttpStatus status) {
        assert actual.statusCode == status
        return this
    }

    ErrorestResponseAssert hasErrorsId() {
        assert errors.id.length() == 10
        return this
    }

    ErrorestResponseAssert hasNoErrors() {
        assert errors.id == 'N/A'
        assert errors.errors.empty
        return this
    }

    ErrorestResponseAssert hasSingleErrorWithoutDescription(String code) {
        assert errors.errors.size() == 1
        assert errors.errors[0] == new Error(code, 'N/A')
        return this
    }

    ErrorestResponseAssert hasSingleError(String code, String description) {
        assert errors.errors.size() == 1
        assert errors.errors[0] == new Error(code, description)
        return this
    }

    ErrorestResponseAssert hasSingleErrorWithDescriptionPrefix(String code, String descriptionPrefix) {
        assert errors.errors.size() == 1
        assert errors.errors[0].hasCode(code)
        assert errors.errors[0].description.startsWith(descriptionPrefix)
        return this
    }

    private Errors getErrors() {
        return actual.responseBodyAsErrors
    }
}
