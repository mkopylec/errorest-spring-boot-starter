package com.github.mkopylec.errorest.response;

public class Error {

    protected final String code;
    protected final String description;

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
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

    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
