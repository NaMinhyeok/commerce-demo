package com.nmh.commerce.user

import com.nmh.commerce.cache.CacheName
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Cacheable(cacheNames = [CacheName.USER], key = "#id")
    fun getUser(id: Long) = userRepository.findById(id)

    @Cacheable(cacheNames = ["ANYCACHENAME"], key = "#id")
    fun getUserWithDefaultCacheName(id: Long): User = userRepository.findById(id)
}
