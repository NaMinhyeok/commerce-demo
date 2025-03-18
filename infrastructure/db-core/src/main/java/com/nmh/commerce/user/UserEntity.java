package com.nmh.commerce.user;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    private String name;

    User toDomain() {
        return User.builder()
            .id(getId())
            .name(name)
            .build();
    }
}
