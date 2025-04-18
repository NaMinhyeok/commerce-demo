package com.nmh.commerce.exception

import com.nmh.commerce.support.error.CustomException
import com.nmh.commerce.support.error.ErrorType

class DomainException : CustomException {
    constructor(errorType: ErrorType) : super(errorType)

    constructor(errorType: ErrorType, data: Any?) : super(errorType, data)
}
