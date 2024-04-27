package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.Role;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class UsersManageServiceImpl implements UsersManageService {

    UserRepository userRepository;
    UserPort userPort;
    List<User> allUsers;
    UserDboMapper userDboMapper;

    @Override
    public List<User> readUsers() {
        List<UserEntity> usersReceiver = userRepository.findAll();
        for (UserEntity userEntity : usersReceiver) {
            allUsers.add(userDboMapper.toDomain(userEntity));
        }

        return allUsers;
    }

    @Override
    public List<User> userByRole(String rol) {

        List<UserEntity> usersReceiver = userRepository.findAll();

        UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();

        for (UserEntity userEntity : usersReceiver) {
            for (int i = 0; i < userDetailsServiceImpl.getRoles(userDboMapper.toDomain(userEntity)).size(); i++) {
                if (userDetailsServiceImpl.getRoles(userDboMapper.toDomain(userEntity)).get(i).getAuthority().equals(rol)) {
                    allUsers.add(userDboMapper.toDomain(userEntity));
                }
            }
        }


        return allUsers;
    }

    @Override
    public User modifyUser(User user)
    {
        UserEntity userFound  = userRepository.findUserEntityByEmail(user.getEmail()).get();
        User userToModify = userDboMapper.toDomain(userFound);
        return userPort.save(userToModify);
    }

    @Override
    public User getOneUser(String email) {
        UserEntity userFound  =  userRepository.findUserEntityByEmail(email).get();
        return  userDboMapper.toDomain(userFound);
    }

    @Override
    public void deleteUser(String userEmail)
    {
        UserEntity userFound  =  userRepository.findUserEntityByEmail(userEmail).get();
        userRepository.deleteById(userFound.getId());
    }

    @Override
    public User createUser(User user) {
        return userPort.save(user);
    }


}
