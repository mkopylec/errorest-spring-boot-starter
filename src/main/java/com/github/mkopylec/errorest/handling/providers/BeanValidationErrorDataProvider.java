package com.github.mkopylec.errorest.handling.providers;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public abstract class BeanValidationErrorDataProvider<T extends Throwable> extends ErrorDataProvider<T> {

    public BeanValidationErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    protected Error createError(ObjectError error) {
        return new Error(error.getDefaultMessage(), "Invalid " + getField(error) + " value: " + getRejectedValue(error));
    }

    protected String getField(ObjectError error) {
        return error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
    }

    protected Object getRejectedValue(ObjectError error) {
        return error instanceof FieldError ? ((FieldError) error).getRejectedValue() : NOT_AVAILABLE_DATA;
    }
}
