package com.groupc.fourparks.api.login.model.repository;

import com.groupc.fourparks.api.login.model.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
}
