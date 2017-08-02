package com.github.mkopylec.errorest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.mkopylec.errorest.configuration.ErrorestProperties.ResponseBodyFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Error {

    public static final String DESCRIPTION_NOT_AVAILABLE = "N/A";

    protected final String code;
    protected String description;

    @JsonCreator
    public Error(
            @JsonProperty("code") @JacksonXmlProperty(localName = "code") String code,
            @JsonProperty("description") @JacksonXmlProperty(localName = "description") String description
    ) {
        this.code = code;
        this.description = isNotBlank(description) ? description : DESCRIPTION_NOT_AVAILABLE;
    }

    public String getCode() {
        return code;
    }

    public boolean hasCode(String code) {
        return this.code.equals(code);
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return !description.equals(DESCRIPTION_NOT_AVAILABLE);
    }

    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    public void format(ResponseBodyFormat bodyFormat) {
        switch (bodyFormat) {
            case FULL:
                return;
            case WITHOUT_DESCRIPTIONS:
                description = DESCRIPTION_NOT_AVAILABLE;
                return;
            default:
                throw new IllegalArgumentException("Unsupported response body format: " + bodyFormat);
        }
    }

    @Override
    public String toString() {
        return code + ": " + description;
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
        Error rhs = (Error) obj;
        return new EqualsBuilder()
                .append(this.code, rhs.code)
                .append(this.description, rhs.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(code)
                .append(description)
                .toHashCode();
    }
}
