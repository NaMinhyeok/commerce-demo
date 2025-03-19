package com.nmh.commerce.user;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    private String name;

    @Builder
    private UserEntity(String name) {
        this.name = name;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .name(user.getName())
            .build();
    }

    public User toDomain() {
        return User.builder()
            .id(getId())
            .name(name)
            .build();
    }
}
