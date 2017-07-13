package com.github.mkopylec.errorest.response;

import com.github.mkopylec.errorest.logging.ErrorsLoggingList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Errors {

    protected final List<Error> errors;

    public Errors(List<Error> errors) {
        this.errors = errors == null ? new ErrorsLoggingList() : errors;
    }

    public List<Error> getErrors() {
        return unmodifiableList(errors);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean containsErrorCode(String code) {
        return errors.stream().anyMatch(error -> error.hasCode(code));
    }

    public boolean containsErrorDescription(String description) {
        return errors.stream().anyMatch(error -> error.hasDescription(description));
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("errors", errors);
        return map;
    }

    public static Errors emptyErrors() {
        return new Errors(emptyList());
    }
}
