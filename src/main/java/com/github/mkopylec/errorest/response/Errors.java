package com.github.mkopylec.errorest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat;
import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.util.Assert.hasText;

@JacksonXmlRootElement(localName = "body")
public class Errors {

    public static final String EMPTY_ERRORS_ID = "N/A";

    protected final String id;
    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(localName = "errors")
    protected final List<Error> errors;

    @JsonCreator
    public Errors(
            @JsonProperty("id") @JacksonXmlProperty(localName = "id", isAttribute = true) String id,
            @JsonProperty("errors") @JacksonXmlProperty(localName = "Jackson bug workaround") List<Error> errors
    ) {
        hasText(id, "Empty errors ID");
        this.id = id;
        this.errors = errors == null ? new ErrorsLoggingList() : new ErrorsLoggingList(errors);
    }

    public String getId() {
        return id;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean containsErrorCode(String code) {
        return errors.stream().anyMatch(error -> error.hasCode(code));
    }

    public boolean containsErrorDescriptions() {
        return errors.stream().anyMatch(Error::hasDescription);
    }

    public boolean containsErrorDescription(String description) {
        return errors.stream().anyMatch(error -> error.hasDescription(description));
    }

    @JsonIgnore
    public boolean isEmpty() {
        return EMPTY_ERRORS_ID.equals(id) && !hasErrors();
    }

    public void formatErrors(ResponseBodyFormat bodyFormat) {
        errors.forEach(error -> error.format(bodyFormat));
    }

    public static Errors emptyErrors() {
        return new Errors(EMPTY_ERRORS_ID, emptyList());
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
                .append(this.id, rhs.id)
                .append(this.errors, rhs.errors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(errors)
                .toHashCode();
    }
}
