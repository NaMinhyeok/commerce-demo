package com.nmh.commerce.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity?, Long?>
