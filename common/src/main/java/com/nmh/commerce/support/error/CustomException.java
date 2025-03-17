package com.nmh.commerce.support.error;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    protected final ErrorType errorType;

    protected final Object data;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public CustomException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }
}
