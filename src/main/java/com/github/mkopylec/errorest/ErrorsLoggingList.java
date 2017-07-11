package com.github.mkopylec.errorest;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class ErrorsLoggingList extends ArrayList<Error> {

    public ErrorsLoggingList() {
        super(1);
    }

    @Override
    public String toString() {
        return stream().map(Error::toString).reduce(EMPTY, (error, nextError) -> error + " | " + nextError);
    }
}
