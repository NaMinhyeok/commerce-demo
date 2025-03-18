package com.nmh.commerce.exception;

import com.nmh.commerce.support.error.CustomException;
import com.nmh.commerce.support.error.ErrorType;

public class DomainException extends CustomException {
    public DomainException(ErrorType errorType) {
        super(errorType);
    }

    public DomainException(ErrorType errorType, Object data) {
        super(errorType, data);
    }
}
