package com.nmh.commerce.support.response

import com.nmh.commerce.support.error.ErrorMessage
import com.nmh.commerce.support.error.ErrorType
import lombok.Getter

@Getter
class ApiResponse<S> private constructor(
    private val result: ResultType,
    private val data: S?,
    private val error: ErrorMessage?,
) {
    companion object {
        fun success(): ApiResponse<*> = ApiResponse<Any?>(ResultType.SUCCESS, null, null)

        fun <S> success(data: S?): ApiResponse<S?> = ApiResponse(ResultType.SUCCESS, data, null)

        fun error(error: ErrorType): ApiResponse<*> = ApiResponse<Any?>(ResultType.ERROR, null, ErrorMessage(error))

        fun error(
            error: ErrorType,
            errorData: Any?,
        ): ApiResponse<*> = ApiResponse<Any?>(ResultType.ERROR, null, ErrorMessage(error, errorData))
    }
}
