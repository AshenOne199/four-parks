package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.RoleEnum;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.UserRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserJpaAdapter implements UserPort {

    private final UserRepository userRepository;

    private final UserDboMapper userDboMapper;

    public UserJpaAdapter(final UserRepository userRepository, final UserDboMapper userDboMapper) {
        this.userRepository = userRepository;
        this.userDboMapper = userDboMapper;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        var optionalUser = userRepository.findUserEntityByEmail(email);
        return optionalUser.map(userDboMapper::toDomain);
    }

    @Override
    public User findUserByEmail(String email) {
        var optionalUser = userRepository.findUserEntityByEmail(email);

        if (optionalUser.isEmpty()){
            throw new NotFoundException("Email: " + email +" no registrado");
        }

        return userDboMapper.toDomain(optionalUser.get());
    }

    @Override
    public User save(User user) {
        var userToSave = userDboMapper.toDbo(user);
        var userSaved = userRepository.save(userToSave);
        return userDboMapper.toDomain(userSaved);
    }

    @Override
    public User findUserByRoleName(String role) {
        var roleEnum = RoleEnum.valueOf(role);
        var optionalUser = userRepository.findUserByRolesRoleEnum(roleEnum);
        if (optionalUser.isEmpty()){
            throw new NotFoundException("Admin no registrado");
        }
        return userDboMapper.toDomain(optionalUser.get());
    }
}
