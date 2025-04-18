package com.nmh.commerce.support.error

class ErrorMessage {
    private val code: String
    private val message: String?
    private val data: Any?

    constructor(errorType: ErrorType) {
        this.code = errorType.code.name
        this.message = errorType.message
        this.data = null
    }

    constructor(errorType: ErrorType, data: Any?) {
        this.code = errorType.code.name
        this.message = errorType.message
        this.data = data
    }
}
