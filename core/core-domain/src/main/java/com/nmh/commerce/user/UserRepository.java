package com.nmh.commerce.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
}
