package com.nmh.commerce.user

import com.nmh.commerce.CoreApplicationIntegrationTest
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.mockito.Mockito.times
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserServiceTest(
    private val userService: UserService,
    private val userRepository: UserRepository,
) : CoreApplicationIntegrationTest() {

    @Test
    fun `getUser는 저장된 user를 반환한다`() {
        // Given
        val user = User(id = 0, name = "Test User")
        val savedUser = userRepository.save(user)

        // When
        val result = userService.getUser(savedUser.id)

        // Then
        then(result).isEqualTo(savedUser)
    }
}
