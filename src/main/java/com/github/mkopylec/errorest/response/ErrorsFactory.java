package com.github.mkopylec.errorest.response;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.logging.ExceptionLogger;

public class ErrorsFactory {

    protected final ErrorestProperties errorestProperties;
    protected final ExceptionLogger logger;

    public ErrorsFactory(ErrorestProperties errorestProperties, ExceptionLogger logger) {
        this.errorestProperties = errorestProperties;
        this.logger = logger;
    }

    public Errors logAndCreateErrors(ErrorData errorData) {
        logger.log(errorData);
        Errors errors = new Errors(errorData.getId(), errorData.getErrors());
        errors.formatErrors(errorestProperties.getResponseBodyFormat());
        return errors;
    }
}
