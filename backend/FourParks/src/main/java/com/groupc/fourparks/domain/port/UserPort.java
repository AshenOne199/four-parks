package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.User;

import java.util.Optional;

public interface UserPort {
    Optional<User> findUserByEmailOptional(String username);
    User findUserByEmail(String email);
    User save(User user);
}
