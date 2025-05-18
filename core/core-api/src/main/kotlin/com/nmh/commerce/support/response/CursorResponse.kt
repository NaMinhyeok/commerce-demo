package com.nmh.commerce.support.response

data class CursorResponse<T>(
    val result: List<T>,
    val pagination: CursorPaginationInfo,
) {
    companion object {
        fun <T> of(result: List<T>, nextCursor: String?, hasNext: Boolean): CursorResponse<T> = CursorResponse(
            result = result,
            pagination = CursorPaginationInfo(nextCursor, hasNext),
        )
    }

    data class CursorPaginationInfo(
        val nextCursor: String?,
        val hasNext: Boolean,
    )
}
