package com.github.mkopylec.errorest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat;
import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Errors {

    public static final String ROOT_ERRORS_FIELD = "errors";
    public static final String ERRORS_ITEM_FIELD = "error";

    @JacksonXmlProperty(localName = ERRORS_ITEM_FIELD)
    @JacksonXmlElementWrapper(useWrapping = false)
    protected final List<Error> errors;

    @JsonCreator
    public Errors(
            @JsonProperty("errors") @JacksonXmlProperty List<Error> errors
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

    public void formatErrors(ResponseBodyFormat bodyFormat) {
        errors.forEach(error -> error.format(bodyFormat));
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put(ROOT_ERRORS_FIELD, errors);
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
