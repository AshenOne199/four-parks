package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.Role;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.RoleRepository;
import com.groupc.fourparks.infraestructure.adapter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsersManageServiceImpl implements UsersManageService {

    private final UserRepository userRepository;
    private final  UserPort userPort;

    private final UserDboMapper userDboMapper;
    private final RoleRepository roleRepository;

    @Override
    public List<User> readUsers() {
        List<User> allUsers = new ArrayList<>(List.of());
        List<UserEntity> usersReceiver = userRepository.findAll();
        for (UserEntity userEntity : usersReceiver) {
            allUsers.add(userDboMapper.toDomain(userEntity));
        }

        return allUsers;
    }

    @Override
    public List<User> userByRole(Long rol) {

        List<User> allUsers = new ArrayList<>(List.of());
        List<UserEntity> usersReceiver = userRepository.findAll();

        RoleEntity roleToVerify = roleRepository.getReferenceById(rol);

        for (UserEntity userEntity : usersReceiver) {

                if (userEntity.getRoles().contains(roleToVerify)) {
                    allUsers.add(userDboMapper.toDomain(userEntity));
                }

        }

        return allUsers;
    }
    @Override
    public User getOneUser(String email) {
        if (userRepository.findUserEntityByEmail(email).isEmpty())
        {
            User sampleUser = new User();
            sampleUser.setId(-1L);
            sampleUser.setEmail("Usuario no encontrado con este correo");
            sampleUser.setFirstName("Usuario no encontrado con este correo");
            sampleUser.setFirstLastname("Usuario no encontrado con este correo");
            return sampleUser;
        }
        else
        {
            UserEntity userFound  =  userRepository.findUserEntityByEmail(email).get();
            return  userDboMapper.toDomain(userFound);
        }

    }
    @Override
    public User modifyUser(User user)
    {
        user.setUpdatedAt(LocalDate.now());
        User userFound = getOneUser(user.getEmail());
        if (userFound.getId()==-1L)
        {
            return userFound;
        }
        else
        {
            return userPort.save(user);
        }


    }



    @Override
    public void deleteUser(String userEmail)
    {
        User userFound = getOneUser(userEmail);


        if (userFound.getId()!=-1L)
        {

            userRepository.deleteById(userFound.getId());
        }


    }

   /* @Override
    public User createUser(User user) {
        User sampleUser = new User();
        sampleUser.setEmail("a");
        sampleUser.setPassword("a");

        if (getOneUser(user.getEmail()).getId() == -1L)
        {


            return userPort.save(sampleUser);
        }
        else
        {

            sampleUser.setEmail("Ya hay un usuario con este correo");
            return sampleUser;
        }
    }*/


}
