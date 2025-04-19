package com.nmh.commerce.user

import com.nmh.commerce.BaseEntity
import jakarta.persistence.Entity

@Entity
class UserEntity private constructor(
    private val name: String
) : BaseEntity() {
    fun toDomain(): User {
        return User(id, name)
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(user.name)
        }
    }
}
