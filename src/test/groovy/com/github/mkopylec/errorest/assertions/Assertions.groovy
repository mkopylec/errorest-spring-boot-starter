package com.github.mkopylec.errorest.assertions

import com.github.mkopylec.errorest.exceptions.ErrorestResponseException

class Assertions {

    static ErrorestResponseAssert assertThat(ErrorestResponseException ex) {
        return new ErrorestResponseAssert(ex)
    }
}
