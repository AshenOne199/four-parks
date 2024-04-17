package com.groupc.fourparks.infraestructure.adapter.repository;

import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
}
