package com.nmh.commerce.support.error

class ErrorMessage {
    private val code: String
    private val message: String?
    private val data: Any?

    constructor(errorType: ErrorType) {
        this.code = errorType.getCode().name
        this.message = errorType.getMessage()
        this.data = null
    }

    constructor(errorType: ErrorType, data: Any?) {
        this.code = errorType.getCode().name
        this.message = errorType.getMessage()
        this.data = data
    }
}
