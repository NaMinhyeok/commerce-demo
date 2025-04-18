package com.nmh.commerce.support.error

abstract class CustomException : RuntimeException {
    val errorType: ErrorType

    val data: Any?

    constructor(errorType: ErrorType) : super(errorType.message) {
        this.errorType = errorType
        this.data = null
    }

    constructor(errorType: ErrorType, data: Any?) : super(errorType.message) {
        this.errorType = errorType
        this.data = data
    }
}
