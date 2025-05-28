package com.nmh.commerce.user

import com.nmh.commerce.CoreApplicationIntegrationTest
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.test.context.bean.override.mockito.MockitoBean

class MockUserServiceTest(
    private val userService: UserService,
    @MockitoBean
    private val userRepository: UserRepository,
) : CoreApplicationIntegrationTest() {

    @Test
    fun `캐시가 존재하면 메서드를 다시 호출하지 않는다`() {
        val user = User(id = 1L, name = "Test User")

        given(userRepository.findById(user.id)).willReturn(user)

        // when
        for (i in 1..10) {
            userService.getUser(user.id)
        }

        verify(userRepository, times(1)).findById(user.id)
    }
}
