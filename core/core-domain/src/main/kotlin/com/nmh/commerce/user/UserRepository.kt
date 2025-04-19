package com.nmh.commerce.user

interface UserRepository {
    fun findById(id: Long): User
}
