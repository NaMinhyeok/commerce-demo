package com.nmh.commerce.user

import com.nmh.commerce.CoreApplicationIntegrationTest
import org.assertj.core.api.BDDAssertions.then
import org.assertj.core.api.BDDAssertions.thenThrownBy
import org.junit.jupiter.api.Disabled
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

    @Disabled("ehcache의 경우는 등록되어있지 않은 경우는 예외를 발생시킨다. 따라서 ehcache를 사용하지 않는 경우 이 테스트는 비활성화한다.")
    @Test
    fun `캐시가 등록되어있지 않으면 캐시를 찾을 수 없는 예외가 발생한다`() {
        // given
        val user = User(id = 0, name = "Test User")
        val savedUser = userRepository.save(user)

        // when
        // then
        thenThrownBy { userService.getUserWithDefaultCacheName(savedUser.id) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Cannot find cache named 'ANYCACHENAME'")
    }
}
