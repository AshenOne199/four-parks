package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;

import java.util.Optional;

public interface UserPort {
    Optional<UserEntity> findUserByEmail(String email);
    UserEntity save(UserEntity userEntity);
}
