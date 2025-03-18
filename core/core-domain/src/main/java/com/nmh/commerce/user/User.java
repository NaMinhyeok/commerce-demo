package com.nmh.commerce.user;

import lombok.Builder;

@Builder
public class User {
    private final Long id;
    private final String name;

    private User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
