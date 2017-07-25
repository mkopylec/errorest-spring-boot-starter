package com.github.mkopylec.errorest.assertions

import com.github.mkopylec.errorest.exceptions.ExternalHttpRequestException

class Assertions {

    static ErrorHttpResponseAssert assertThat(ExternalHttpRequestException ex) {
        return new ErrorHttpResponseAssert(ex)
    }
}
