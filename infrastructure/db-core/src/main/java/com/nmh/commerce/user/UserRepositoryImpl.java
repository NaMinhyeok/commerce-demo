package com.nmh.commerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }
}
