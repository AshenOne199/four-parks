package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    Optional<User> findUserByEmailOptional(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);
    List<User> findAllUsers();
    void deleteUser(User user);
    User save(User user);
    List<UserEntity> findAll();
    User findUserByRoleName(String role);
}
