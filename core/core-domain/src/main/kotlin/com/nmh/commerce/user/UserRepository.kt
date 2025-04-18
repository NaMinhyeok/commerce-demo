package com.nmh.commerce.user

import java.util.*

interface UserRepository {
    fun findById(id: Long): User
}
