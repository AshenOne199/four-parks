package com.groupc.fourparks.infraestructure.adapter.repository;

import com.groupc.fourparks.domain.model.RoleEnum;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    Optional<UserEntity> findUserByRolesRoleEnum(RoleEnum roleEnum);
}
