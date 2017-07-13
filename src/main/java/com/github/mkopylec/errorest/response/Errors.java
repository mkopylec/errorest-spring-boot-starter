package com.github.mkopylec.errorest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Errors {

    protected final List<Error> errors;

    @JsonCreator
    public Errors(
            @JsonProperty("errors") @JacksonXmlProperty(localName = "errors") List<Error> errors
    ) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Errors rhs = (Errors) obj;
        return new EqualsBuilder()
                .append(this.errors, rhs.errors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(errors)
                .toHashCode();
    }
}
