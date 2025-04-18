package com.nmh.commerce.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.function.Function

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository) : UserRepository {
    override fun findById(id: Long): User {
        return userJpaRepository.findByIdOrNull(id)?.toDomain() ?: throw NoSuchElementException("User not found")
    }
}
