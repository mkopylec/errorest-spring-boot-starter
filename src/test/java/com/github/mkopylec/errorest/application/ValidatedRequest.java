package com.github.mkopylec.errorest.application;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class ValidatedRequest {

    @NotNull(message = "EMPTY_MESSAGE")
    private String message;
    @Max(message = "NUMBER_TOO_BIG", value = 10)
    private int number;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
