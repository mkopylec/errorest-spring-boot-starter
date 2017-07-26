package com.github.mkopylec.errorest.handling.errordata.validation;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import static com.github.mkopylec.errorest.handling.errordata.ErrorData.ErrorDataBuilder.newErrorData;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public abstract class BeanValidationErrorDataProvider<T extends Throwable> extends ErrorDataProvider<T> {

    public BeanValidationErrorDataProvider(ErrorestProperties errorestProperties) {
        super(errorestProperties);
    }

    protected ErrorDataBuilder buildErrorData(BindingResult result) {
        ErrorestProperties.BeanValidationError validationError = errorestProperties.getBeanValidationError();
        ErrorDataBuilder builder = newErrorData()
                .withLoggingLevel(validationError.getLoggingLevel())
                .withResponseStatus(UNPROCESSABLE_ENTITY);
        result.getAllErrors().forEach(objectError -> {
            Error error = createError(objectError);
            builder.addError(error);
        });
        return builder;
    }

    protected Error createError(ObjectError error) {
        return new Error(error.getDefaultMessage(), "Invalid '" + getField(error) + "' value: " + getRejectedValue(error));
    }

    protected String getField(ObjectError error) {
        return error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
    }

    protected Object getRejectedValue(ObjectError error) {
        return error instanceof FieldError ? ((FieldError) error).getRejectedValue() : NOT_AVAILABLE_DATA;
    }
}
