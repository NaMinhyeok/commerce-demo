package com.nmh.commerce.support.error

enum class ErrorType(
    val status: Int,
    val code: ErrorCode,
    val message: String,
) {
    DEFAULT_ERROR(500, ErrorCode.E500, "알 수 없는 오류가 발생했습니다."),
}
