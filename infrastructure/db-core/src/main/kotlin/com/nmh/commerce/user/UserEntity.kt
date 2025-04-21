package com.nmh.commerce.user

import com.nmh.commerce.BaseEntity
import jakarta.persistence.Entity

@Entity
class UserEntity private constructor(
    override val id: Long = 0,
    private val name: String,
) : BaseEntity<Long>() {
    fun toDomain(): User = User(id, name)

    companion object {
        fun from(user: User): UserEntity = UserEntity(user.id, user.name)
    }
}
