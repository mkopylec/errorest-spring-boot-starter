package com.github.mkopylec.errorest.logging;

import com.github.mkopylec.errorest.response.Error;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class ErrorsLoggingList extends ArrayList<Error> {

    public ErrorsLoggingList() {
        super(1);
    }

    public ErrorsLoggingList(Collection<? extends Error> c) {
        super(c);
    }

    @Override
    public String toString() {
        return stream().map(Error::toString).collect(joining(" | "));
    }
}
