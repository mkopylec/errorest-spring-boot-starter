package com.github.mkopylec.errorest;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Errors {

    protected final List<Error> errors;

    public Errors(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return unmodifiableList(errors);
    }
}
