package com.nmh.commerce.support.response

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val result: List<T>,
    val pagination: PaginationInfo,
) {
    companion object {
        fun <T> of(page: Page<T>): PageResponse<T> = PageResponse(
            result = page.content,
            pagination = PaginationInfo(page),
        )
    }

    data class PaginationInfo(
        val currentPage: Int,
        val pageSize: Int,
        val totalElements: Long,
        val totalPages: Int,
        val last: Boolean,
    ) {
        constructor(page: Page<*>) : this(
            currentPage = page.number,
            pageSize = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            last = page.isLast,
        )
    }
}
