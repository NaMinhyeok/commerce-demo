package com.nmh.commerce.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(id: Long): User = userJpaRepository.findByIdOrNull(id)?.toDomain()
        ?: throw NoSuchElementException("User not found")

    override fun save(user: User): User = userJpaRepository.save(UserEntity.from(user)).toDomain()
}
