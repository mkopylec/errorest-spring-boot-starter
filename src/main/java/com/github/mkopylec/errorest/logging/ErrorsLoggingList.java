package com.github.mkopylec.errorest.logging;

import com.github.mkopylec.errorest.response.Error;

import java.util.ArrayList;

import static java.util.stream.Collectors.joining;

public class ErrorsLoggingList extends ArrayList<Error> {

    public ErrorsLoggingList() {
        super(1);
    }

    @Override
    public String toString() {
        return stream().map(Error::toString).collect(joining(" | "));
    }
}
